import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { SalaServices } from '../../../services/sala/sala-services';
import { SessionService } from '../../../services/session-service/session-service';
import { Sala } from '../../../models/sala';

@Component({
  selector: 'app-crear-sala',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './crear-sala.component.html',
  styleUrl: './crear-sala.component.css'
})
export class CrearSalaComponent {

  nombreSala: string = '';
  filaSala: number = 0;
  columnaSala: number = 0;
  fechaCreacion: string = new Date().toISOString().split('T')[0];
  asientos: any[] = [];
  mensaje: string = '';
  tipoMensaje: 'success' | 'danger' | '' = '';

  constructor(private salaService: SalaServices, private session: SessionService) { }

  actualizarVista() {
    const totalAsientos = this.filaSala * this.columnaSala;
    this.asientos = Array(totalAsientos).fill(null);
  }
  
  crearSala() {
    const usuario = this.session.obtenerUser();
    if (!usuario) {
      this.mensaje = 'No hay usuario en sesión';
      this.tipoMensaje = 'danger';
      return;
    }

    const datosSala: Sala = {
      idSala: 0,
      idUsuario: usuario.idUsuario,
      nombreSala: this.nombreSala,
      filaSala: this.filaSala,
      columnaSala: this.columnaSala,
      fechaCreacion: this.fechaCreacion
    };

    this.salaService.crearSala(datosSala).subscribe({
      next: () => {
        this.mensaje = 'Sala creada correctamente';
        this.tipoMensaje = 'success';

        this.nombreSala = '';
        this.filaSala = 10;
        this.columnaSala = 10;
      },
      error: (err) => {
        this.mensaje = 'Error al crear sala: ' + err.message;
        this.tipoMensaje = 'danger';
      }
    });
  }
}