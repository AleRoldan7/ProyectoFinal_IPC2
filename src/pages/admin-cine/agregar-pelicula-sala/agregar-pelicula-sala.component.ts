import { Component, OnInit } from '@angular/core';
import { Lista } from '../../../services/lista/lista';
import { PeliculaServices } from '../../../services/pelicula/pelicula-services';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SessionService } from '../../../services/session-service/session-service';
import { ProyeccionService } from '../../../services/proyeccion/proyeccion-service';

@Component({
  selector: 'app-agregar-pelicula-sala',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './agregar-pelicula-sala.component.html',
  styleUrl: './agregar-pelicula-sala.component.css',
})
export class AgregarPeliculaSalaComponent implements OnInit {
  peliculas: any[] = [];
  salas: any[] = [];
  salaSeleccionada: any = null;
  peliculaSeleccionada: any = null;
  horaProyeccion: string = '19:00';
  fechaInicio: string = '';
  fechaFin: string = '';
  precio: number = 50.00;
  mensaje: string = '';
  tipoMensaje: 'success' | 'danger' | '' = '';

  constructor(private lista: Lista, private proyectar: ProyeccionService, private sessionService: SessionService) {}

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

  asignarProyeccion() {
    if (!this.salaSeleccionada || !this.peliculaSeleccionada) {
      this.mensaje = 'Selecciona sala y película';
      this.tipoMensaje = 'danger';
      return;
    }

    const usuario = this.sessionService.obtenerUser();
    const payload = {
      idUsuario: usuario.idUsuario,
      idSala: this.salaSeleccionada.idSala,
      idPelicula: this.peliculaSeleccionada.idPelicula,
      hora: this.horaProyeccion,
      fechaInicio: this.fechaInicio,
      fechaFin: this.fechaFin,
      precio: this.precio
    };

    console.log('ENVIANDO PROYECCIÓN:', payload);

    this.proyectar.crearProyeccion(payload).subscribe({
      next: (res) => {
        this.mensaje = res.exito || '¡Proyección creada con éxito!';
        this.tipoMensaje = 'success';
        this.limpiarFormulario();
      },
      error: (err) => {
        this.mensaje = err.error?.error || 'Error al crear proyección';
        this.tipoMensaje = 'danger';
      }
    });
  }

  limpiarFormulario() {
    this.peliculaSeleccionada = null;
    this.horaProyeccion = '19:00';
    this.precio = 50.00;
  }

  
}
