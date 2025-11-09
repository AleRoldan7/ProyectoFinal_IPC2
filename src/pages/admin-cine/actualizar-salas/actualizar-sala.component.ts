import { Component, OnInit } from '@angular/core';
import { SalaServices } from '../../../services/sala/sala-services';
import { SessionService } from '../../../services/session-service/session-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-actualizar-sala',
  imports: [FormsModule, CommonModule],
  templateUrl: './actualizar-sala.component.html',
  styleUrl: './actualizar-sala.component.css'
})
export class ActualizarSalaComponent implements OnInit {

  salas: any[] = [];
  salaSeleccionada: any = {
    idSala: 0,
    nombreSala: '',
    filaSala: 0,
    columnaSala: 0,
    fechaCreacion: ''
  };

  mostrarFormulario = false;

  constructor(
    private salaService: SalaServices,
    private session: SessionService
  ) { }

  ngOnInit() {
    this.cargarSalas();
  }

  cargarSalas() {
    const idCine = this.session.obtenerIdCine();
    if (idCine) {
      this.salaService.obtenerSalasPorCine(idCine).subscribe({
        next: (data) => {
          this.salas = Array.isArray(data) ? data : [data];
          console.log('Salas cargadas:', this.salas);
        },
        error: (e) => {
          console.error('Error cargando salas:', e);
          this.salas = [];
        }
      });
    }
  }

  seleccionarSala(sala: any) {
    this.salaSeleccionada = { ...sala };

    if (this.salaSeleccionada.fechaCreacion) {
      this.salaSeleccionada.fechaCreacion =
        this.salaSeleccionada.fechaCreacion.split('T')[0]; 
    }

    this.mostrarFormulario = true;
    console.log('Sala seleccionada:', this.salaSeleccionada);
  }

  calcularTotalAsientos(): number {
    return this.salaSeleccionada.filaSala * this.salaSeleccionada.columnaSala;
  }

  actualizarSala() {
    if (!this.salaSeleccionada.idSala || !this.salaSeleccionada.nombreSala) {
      alert('ID y nombre son obligatorios');
      return;
    }

    if (this.salaSeleccionada.filaSala <= 0 || this.salaSeleccionada.columnaSala <= 0) {
      alert('Filas y columnas deben ser mayores a 0');
      return;
    }

    console.log('Actualizando sala:', this.salaSeleccionada);

    this.salaService.actualizarSala(this.salaSeleccionada).subscribe({
      next: (response) => {
        alert('Sala actualizada correctamente');
        console.log('Respuesta:', response);
        this.mostrarFormulario = false;
        this.cargarSalas();
      },
      error: (e) => {
        console.error('Error actualizando sala:', e);
        alert(e.error?.error || 'Error al actualizar sala');
      }
    });
  }

  cancelarEdicion() {
    this.mostrarFormulario = false;
    this.salaSeleccionada = {
      idSala: 0,
      nombreSala: '',
      filaSala: 0,
      columnaSala: 0,
      fechaCreacion: ''
    };
  }
}
