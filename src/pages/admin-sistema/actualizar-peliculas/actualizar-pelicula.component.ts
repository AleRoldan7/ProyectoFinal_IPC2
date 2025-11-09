import { Component, OnInit } from '@angular/core';
import { PeliculaServices } from '../../../services/pelicula/pelicula-services';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-actualizar-pelicula',
  imports: [FormsModule, CommonModule],
  templateUrl: './actualizar-pelicula.component.html',
  styleUrl: './actualizar-pelicula.component.css'
})
export class ActualizarPeliculaComponent implements OnInit {

  peliculas: any[] = [];
  peliculaSeleccionada: any = {
    idPelicula: 0,
    tituloPelicula: '',
    sinopsisPelicula: '',
    duracionPelicula: '02:00',
    castPelicula: '',
    directorPelicula: ''
  };

  archivoSeleccionado: File | null = null;
  mostrarFormulario = false;
  esEdicion = false;
  terminoBusqueda = '';
  cargando = false;

  // Para asignar películas a salas
  mostrarAsignacion = false;
  idSalaAsignar: number = 0;
  idPeliculaAsignar: number = 0;

  constructor(private peliculaService: PeliculaServices) { }

  ngOnInit() {
    this.cargarPeliculas();
  }

  cargarPeliculas() {
    this.cargando = true;
    this.peliculaService.obtenerTodasLasPeliculas().subscribe({
      next: (data) => {
        this.peliculas = Array.isArray(data) ? data : [data];
        this.cargando = false;
        console.log('Películas cargadas:', this.peliculas);
      },
      error: (e) => {
        console.error('Error cargando películas:', e);
        this.cargando = false;
        // Solo muestra error, sin datos de prueba
        alert('Error al cargar las películas: ' + e.message);
      }
    });
  }

  buscarPeliculas() {
    if (this.terminoBusqueda.trim()) {
      this.peliculaService.buscarPeliculas(this.terminoBusqueda).subscribe({
        next: (data) => {
          this.peliculas = Array.isArray(data) ? data : [data];
        },
        error: (e) => {
          console.error('Error buscando películas:', e);
          alert('Error al buscar películas: ' + e.message);
        }
      });
    } else {
      this.cargarPeliculas();
    }
  }

  nuevaPelicula() {
    this.peliculaSeleccionada = {
      idPelicula: 0,
      tituloPelicula: '',
      sinopsisPelicula: '',
      duracionPelicula: '02:00',
      castPelicula: '',
      directorPelicula: ''
    };
    this.archivoSeleccionado = null;
    this.esEdicion = false;
    this.mostrarFormulario = true;
  }

  editarPelicula(pelicula: any) {
    this.peliculaSeleccionada = { ...pelicula };
    this.esEdicion = true;
    this.mostrarFormulario = true;
  }

  onFileSelected(event: any) {
    this.archivoSeleccionado = event.target.files[0];
    console.log('Archivo seleccionado:', this.archivoSeleccionado);
  }

  guardarPelicula() {
    if (!this.peliculaSeleccionada.tituloPelicula || !this.peliculaSeleccionada.duracionPelicula) {
      alert('Título y duración son obligatorios');
      return;
    }

    console.log('Guardando película:', this.peliculaSeleccionada);

    if (this.esEdicion) {
      // Actualizar película
      this.peliculaService.actualizarPelicula(this.peliculaSeleccionada, this.archivoSeleccionado).subscribe({
        next: (response) => {
          alert('Película actualizada correctamente');
          console.log('Respuesta actualización:', response);
          this.mostrarFormulario = false;
          this.cargarPeliculas();
        },
        error: (e) => {
          console.error('Error actualizando película:', e);
          alert(e.error?.error || 'Error al actualizar película');
        }
      });
    } else {
      // Crear nueva película
      if (!this.archivoSeleccionado) {
        alert('El poster es obligatorio para crear una película');
        return;
      }

      this.peliculaService.crearPeliculaConDatos(this.peliculaSeleccionada, this.archivoSeleccionado).subscribe({
        next: (response) => {
          alert('Película creada correctamente');
          console.log('Respuesta creación:', response);
          this.mostrarFormulario = false;
          this.cargarPeliculas();
        },
        error: (e) => {
          console.error('Error creando película:', e);
          alert(e.error?.error || 'Error al crear película');
        }
      });
    }
  }

  eliminarPelicula(idPelicula: number, titulo: string) {
    if (confirm(`¿Estás seguro de eliminar la película "${titulo}"?`)) {
      this.peliculaService.eliminarPelicula(idPelicula).subscribe({
        next: (response) => {
          alert('Película eliminada correctamente');
          this.cargarPeliculas();
        },
        error: (e) => {
          console.error('Error eliminando película:', e);
          alert(e.error?.error || 'Error al eliminar película');
        }
      });
    }
  }

  // Métodos para asignar películas a salas
  abrirAsignacion(pelicula: any) {
    this.idPeliculaAsignar = pelicula.idPelicula;
    this.mostrarAsignacion = true;
  }

  asignarPeliculaSala() {
    if (!this.idSalaAsignar || this.idSalaAsignar <= 0) {
      alert('Por favor ingresa un ID de sala válido');
      return;
    }

    this.peliculaService.agregarPeliculaSala(this.idSalaAsignar, this.idPeliculaAsignar).subscribe({
      next: (response) => {
        alert('Película asignada a sala correctamente');
        this.mostrarAsignacion = false;
        this.idSalaAsignar = 0;
      },
      error: (e) => {
        console.error('Error asignando película a sala:', e);
        alert(e.error?.error || 'Error al asignar película a sala');
      }
    });
  }

  cancelar() {
    this.mostrarFormulario = false;
    this.mostrarAsignacion = false;
    this.peliculaSeleccionada = {
      idPelicula: 0,
      tituloPelicula: '',
      sinopsisPelicula: '',
      duracionPelicula: '02:00',
      castPelicula: '',
      directorPelicula: ''
    };
    this.archivoSeleccionado = null;
    this.idSalaAsignar = 0;
    this.idPeliculaAsignar = 0;
  }
}
