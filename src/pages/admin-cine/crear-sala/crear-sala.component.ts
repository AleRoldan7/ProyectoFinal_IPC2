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
  filaSala: number = 10;
  columnaSala: number = 10;
  fechaCreacion: string = new Date().toISOString().split('T')[0];
  asientos: any[] = [];
  mensaje: string = '';
  tipoMensaje: 'success' | 'danger' | '' = '';

  constructor(private salaService: SalaServices,private session: SessionService) {
    this.actualizarVista();
  }

  actualizarVista() {
    const total = this.filaSala * this.columnaSala;
    this.asientos = Array(total).fill(null);
  }

  crearSala() {
    const usuario = this.session.obtenerUser();
    if (!usuario) {
      this.mensaje = 'No hay usuario en sesión';
      this.tipoMensaje = 'danger';
      return;
    }

    if (!this.nombreSala?.trim()) {
      this.mensaje = 'El nombre de la sala es obligatorio';
      this.tipoMensaje = 'danger';
      return;
    }

    const nuevaSala = {
      idUsuario: usuario.idUsuario,
      nombreSala: this.nombreSala.trim(),
      filaSala: this.filaSala,
      columnaSala: this.columnaSala,
      fechaCreacion: this.fechaCreacion
    };

    this.salaService.crearSala(nuevaSala).subscribe({
      next: () => {
        this.mensaje = '¡Sala creada exitosamente!';
        this.tipoMensaje = 'success';
        this.limpiarFormulario();
      },
      error: (err) => {
        this.mensaje = 'Error: ' + (err.error?.error || 'Verifica los datos');
        this.tipoMensaje = 'danger';
      }
    });
  }

  limpiarFormulario() {
    this.nombreSala = '';
    this.filaSala = 10;
    this.columnaSala = 10;
    this.actualizarVista();
  }
}