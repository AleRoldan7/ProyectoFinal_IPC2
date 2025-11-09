import { Component } from '@angular/core';
import { SessionService } from '../../services/session-service/session-service';
import { BoletoService } from '../../services/boleto/boleto-service';
import { ComentarioService } from '../../services/comentario-service/comentario-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-peliculas-usuario',
  imports: [FormsModule, CommonModule],
  templateUrl: './peliculas-usuario.component.html',
  styleUrl: './peliculas-usuario.component.css'
})
export class PeliculasUsuarioComponent {
  peliculas: any[] = [];
  peliculaSeleccionada: any = null;
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
      this.cargarPeliculasCompradas(idUsuario);
    }
  }

  cargarPeliculasCompradas(idUsuario: number) {
    this.boletoService.obtenerPeliculasCompradas(idUsuario).subscribe({
      next: (data) => this.peliculas = data
    });
  }

  seleccionarPelicula(p: any) {
    this.peliculaSeleccionada = p;
    this.cargarComentarios(p.idProyeccion);
  }

  cargarComentarios(idProyeccion: number) {
    this.comentarioService.obtenerComentariosPelicula(idProyeccion).subscribe({
      next: (data) => this.comentarios = data
    });
  }

  formatearFecha(fecha: string): string {
    const d = new Date(fecha);
    return `${d.getDate().toString().padStart(2, '0')}/${(d.getMonth() + 1).toString().padStart(2, '0')}/${d.getFullYear()} ${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')}`;
  }

  enviarComentario() {
    if (!this.nuevoComentario.comentario.trim()) return;

    const data = {
      idUsuario: this.sessionService.obtenerUser().idUsuario,
      idProyeccion: this.peliculaSeleccionada.idProyeccion,
      comentario: this.nuevoComentario.comentario,
      calificacion: this.nuevoComentario.calificacion
    };

    this.comentarioService.comentarPelicula(data).subscribe({
      next: () => {
        this.nuevoComentario.comentario = '';
        this.cargarComentarios(this.peliculaSeleccionada.idProyeccion);
      }
    });
  }
}
