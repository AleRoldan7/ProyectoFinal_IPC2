import { Component } from '@angular/core';
import { Pelicula } from '../../../models/pelicula';
import { PeliculaServices } from '../../../services/pelicula/pelicula-services';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-crear-pelicula',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './crear-pelicula.component.html',
  styleUrl: './crear-pelicula.component.css'
})
export class CrearPeliculaComponent {

  pelicula: Pelicula = {
    tituloPelicula: '',
    sinopsisPelicula: '',
    duracionPelicula: '',
    castPelicula: '',
    directorPelicula: '',
    posterPelicula: null,
  }

  mensaje: string = '';
  tipoMensaje: 'success' | 'danger' | '' = '';
  cargando: boolean = false;

  constructor(private peliculaService: PeliculaServices) { }

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.pelicula.posterPelicula = file;
    }
  }

  agregarPelicula() {
    const formData = new FormData();
    this.cargando = true;
    formData.append('tituloPelicula', this.pelicula.tituloPelicula);
    formData.append('sinopsisPelicula', this.pelicula.sinopsisPelicula);
    formData.append('duracionPelicula', this.pelicula.duracionPelicula);
    formData.append('castPelicula', this.pelicula.castPelicula);
    formData.append('directorPelicula', this.pelicula.directorPelicula);

    if (this.pelicula.posterPelicula) {
      formData.append('posterPelicula', this.pelicula.posterPelicula);
    }

    this.peliculaService.crearPelicula(formData).subscribe({
      next: (response) => {
        this.mensaje = 'Película creada con éxito';
        this.tipoMensaje = 'success';
      },
      error: (err) => {
        this.mensaje = err.error;
        this.tipoMensaje = 'danger';
      }
    });
  }

  mostrarMensaje(msg: string, tipo: 'success' | 'danger') {
    this.mensaje = msg;
    this.tipoMensaje = tipo;
    setTimeout(() => this.mensaje = '', 5000);
  }
}
