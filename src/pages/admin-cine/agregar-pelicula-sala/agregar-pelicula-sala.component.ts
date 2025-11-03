import { Component, OnInit } from '@angular/core';
import { Lista } from '../../../services/lista/lista';
import { PeliculaServices } from '../../../services/pelicula/pelicula-services';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SessionService } from '../../../services/session-service/session-service';

@Component({
  selector: 'app-agregar-pelicula-sala',
  imports: [FormsModule, CommonModule],
  templateUrl: './agregar-pelicula-sala.component.html',
  styleUrl: './agregar-pelicula-sala.component.css',
})
export class AgregarPeliculaSalaComponent implements OnInit {
  peliculas: any[] = [];
  salas: any[] = [];
  salaSeleccionada: any = null;
  peliculaSeleccionada: any = null;
  mensaje: string = '';
  tipoMensaje: 'success' | 'danger' | '' = '';

  constructor(private lista: Lista, private peliculaService: PeliculaServices, private sessionService: SessionService) {}

  ngOnInit(): void {
    const usuario = this.sessionService.obtenerUser();
    console.log('Usuario logueado:', usuario);

    if (usuario && usuario.idUsuario) {
      this.cargarSalas(usuario.idUsuario);
    } else {
      console.warn('No hay usuario logueado o falta idUsuario');
    }

    this.cargarPeliculas();
  }

  cargarSalas(idUsuario: number) {
    console.log('Cargando salas para idUsuario:', idUsuario);
    this.lista.obtenerSalasAdmin(idUsuario).subscribe({
      next: (salas) => {
        console.log('Salas recibidas del backend:', salas);
        this.salas = Array.isArray(salas) ? salas : [salas];
      },
      error: (err) => console.error('Error al cargar salas', err),
    });
  }

  cargarPeliculas() {
    this.lista.obtenerPeliculas().subscribe({
      next: (peliculas) => {
        console.log('Películas recibidas:', peliculas);
        this.peliculas = Array.isArray(peliculas) ? peliculas : [peliculas];
      },
      error: (err) => console.error('No cargo películas', err),
    });
  }

  asignarPelicula() {
    if (!this.salaSeleccionada || !this.peliculaSeleccionada) {
      this.mensaje = 'Debe seleccionar una sala y una película';
      this.tipoMensaje = 'danger';
      return;
    }

    this.peliculaService
      .agregarPeliculaSala(this.salaSeleccionada.idSala, this.peliculaSeleccionada.idPelicula)
      .subscribe({
        next: (response) => {
          console.log('Respuesta backend al asignar película:', response);
          this.mensaje = response.exito || 'Película asignada correctamente';
          this.tipoMensaje = 'success';
        },
        error: (err) => {
          console.error(err);
          this.mensaje = 'Error al asignar la película';
          this.tipoMensaje = 'danger';
        },
      });
  }
}
