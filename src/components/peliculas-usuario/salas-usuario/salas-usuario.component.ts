import { Component } from '@angular/core';
import { SessionService } from '../../../services/session-service/session-service';
import { BoletoService } from '../../../services/boleto/boleto-service';
import { ComentarioService } from '../../../services/comentario-service/comentario-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-salas-usuario',
  imports: [FormsModule, CommonModule],
  templateUrl: './salas-usuario.component.html',
  styleUrl: './salas-usuario.component.css'
})
export class SalasUsuarioComponent {

  salas: any[] = [];
  salaSeleccionada: any = null;
  comentarios: any[] = [];
  nuevoComentario = { comentario: '', calificacion: 5 };
  isLoggedIn = false;

  constructor(
    private sessionService: SessionService,
    private boletoService: BoletoService,
    private comentarioService: ComentarioService
  ) { }

  ngOnInit(): void {
    this.isLoggedIn = this.sessionService.sessionActivate();
    if (this.isLoggedIn) {
      const idUsuario = this.sessionService.obtenerUser().idUsuario;
      this.cargarSalasAsistidas(idUsuario);
    }
  }

  cargarSalasAsistidas(idUsuario: number) {
    this.boletoService.obtenerPeliculasCompradas(idUsuario).subscribe({
      next: (data) => {
        this.salas = data.map((b: any) => ({
          idSalaCine: b.idSalaCine,
          nombreSala: b.sala,
          imagenSala: b.poster,
          cine: b.cine,
          hora: b.hora,
          idProyeccion: b.idProyeccion
        }));

      }
    });
  }

  seleccionarSala(s: any) {
    this.salaSeleccionada = s;
    this.cargarComentarios(s.idSala);
  }

  cargarComentarios(idSala: number) {
    this.comentarioService.obtenerComentariosSala(idSala).subscribe({
      next: (data) => this.comentarios = data
    });
  }

  formatearFecha(fecha: string): string {
    const d = new Date(fecha);
    return `${d.getDate().toString().padStart(2, '0')}/${(d.getMonth() + 1).toString().padStart(2, '0')
      }/${d.getFullYear()} ${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')}`;
  }

  enviarComentario() {
    if (!this.nuevoComentario.comentario.trim()) return;

    const data = {
      idUsuario: this.sessionService.obtenerUser().idUsuario,
      idSalaCine: this.salaSeleccionada.idSalaCine,
      comentario: this.nuevoComentario.comentario,
      calificacion: this.nuevoComentario.calificacion
    };


    this.comentarioService.comentarSala(data).subscribe({
      next: () => {
        this.nuevoComentario.comentario = '';
        this.cargarComentarios(this.salaSeleccionada.idSalaCine);
      }
    });
  }
}
