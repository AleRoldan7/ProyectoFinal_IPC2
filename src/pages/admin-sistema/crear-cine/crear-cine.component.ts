import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CineServices } from '../../../services/cine/cine-services';
import { Lista } from '../../../services/lista/lista';
import { RouterLink } from '@angular/router';
import { authGuard } from '../../../guards/auth-guard';

@Component({
  selector: 'app-crear-cine',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './crear-cine.component.html',
  styleUrl: './crear-cine.component.css',
})
export class CrearCineComponent {
  admins: any[] = [];
  nombreCine: string = '';
  costoCine: number = 100.0;
  fechaCreacion: string = new Date().toISOString().split('T')[0];
  adminSeleccionado: any = null;
  mensaje: string = '';
  tipoMensaje: 'success' | 'danger' | '' = '';
  rolUsuario = 'ADMIN_CINE';

  constructor(private lista: Lista, private cineService: CineServices, authGuard: authGuard) {}

  ngOnInit(): void {
    this.cargarAdmins();
  }

  cargarAdmins() {
    this.lista.obtenerAdminCine(this.rolUsuario).subscribe({
      next: (admins) => {
        this.admins = Array.isArray(admins) ? admins : [admins];
      },
      error: (err) => {
        console.error('Error al cargar administradores', err);
      },
    });
  }

  seleccionarAdmin(admin: any) {
    this.adminSeleccionado = admin;
  }

  asignarCine() {
    if (!this.adminSeleccionado || !this.nombreCine) {
      this.mensaje = 'Debe escribir el nombre del cine';
      this.tipoMensaje = 'danger';
      return;
    }

    const cineData = {
      nombreCine: this.nombreCine,
      costoCine: this.costoCine,
      fechaCreacion: this.fechaCreacion,
      idUsuario: this.adminSeleccionado.idUsuario,
    };

    this.cineService.crearCine(cineData).subscribe({
      next: (response) => {
        this.mensaje = response;
        this.tipoMensaje = 'success';

        this.nombreCine = '';
        this.adminSeleccionado = null;
      },
      error: (err) => {
        this.mensaje = err.message;
        this.tipoMensaje = 'danger';
      }

    });
  }
}
