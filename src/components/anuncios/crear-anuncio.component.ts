import { Component } from '@angular/core';
import { Anuncio } from '../../models/anuncio';
import { tipoAnuncio } from '../../models/tipo.anuncio';
import { AnuncioServices } from '../../services/anuncio/anuncio-services';
import { SessionService } from '../../services/session-service/session-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-crear-anuncio',
  imports: [FormsModule, CommonModule],
  templateUrl: './crear-anuncio.component.html',
  styleUrl: './crear-anuncio.component.css'
})

export class CrearAnuncioComponent {
  tipoAnuncio = tipoAnuncio;
  tipos = Object.values(tipoAnuncio);

  anuncioSeleccionado: tipoAnuncio = tipoAnuncio.TEXTO;
  mensajeAnuncio = '';
  fechaInicio: string = '';
  diasVigente: number = 1;
  imagenAnuncio: File | null = null;
  videoAnuncio: string = '';
  mensaje: string = '';
  precio: number = 0;
  enviando = false;
  vistaPrevia: string | null = null;
  hoy = new Date().toISOString().split('T')[0];

  constructor(
    private anuncioService: AnuncioServices,
    private sessionService: SessionService
  ) {
    this.calcularPrecio();
  }

  fileSeleccionado(event: any) {
    const file = event.target.files[0];
    if (!file) return;

    if (!file.type.startsWith('image/')) {
      alert('Solo se permiten imágenes');
      return;
    }

    if (file.size > 2 * 1024 * 1024) {
      alert('La imagen no debe exceder 2MB');
      return;
    }

    this.imagenAnuncio = file;

    const reader = new FileReader();
    reader.onload = (e: any) => this.vistaPrevia = e.target.result;
    reader.readAsDataURL(file);
  }

  calcularPrecio() {
    const precios: Record<tipoAnuncio, number> = {
      [tipoAnuncio.TEXTO]: 10,
      [tipoAnuncio.TEXTO_IMAGEN]: 20,
      [tipoAnuncio.TEXTO_VIDEO]: 30
    };
    this.precio = (precios[this.anuncioSeleccionado] || 0) * this.diasVigente;
  }

  crearAnuncio() {
    const usuario = this.sessionService.obtenerUser();
    if (!usuario) {
      alert('Debes iniciar sesión');
      return;
    }

    this.enviando = true;
    this.mensaje = '';

    const formData = new FormData();
    formData.append('tipoAnuncio', this.anuncioSeleccionado);
    formData.append('mensajeAnuncio', this.mensajeAnuncio);
    formData.append('nombreAnunciante', usuario.idUsuario.toString());
    formData.append('fechaInicio', this.fechaInicio);
    formData.append('diasVigente', this.diasVigente.toString());

    if (this.imagenAnuncio) {
      formData.append('imagenAnuncio', this.imagenAnuncio, this.imagenAnuncio.name);
    }
    if (this.videoAnuncio) {
      formData.append('videoAnuncio', this.videoAnuncio);
    }

    this.anuncioService.crearAnuncio(formData).subscribe({
      next: () => {
        this.mensaje = '¡Anuncio creado exitosamente!';
        this.limpiarFormulario();
      },
      error: (e) => {
        this.mensaje = e.error?.error || 'Error al crear el anuncio';
      },
      complete: () => this.enviando = false
    });
  }

  limpiarFormulario() {
    this.anuncioSeleccionado = tipoAnuncio.TEXTO;
    this.mensajeAnuncio = '';
    this.fechaInicio = '';
    this.diasVigente = 1;
    this.imagenAnuncio = null;
    this.videoAnuncio = '';
    this.vistaPrevia = null;
    this.calcularPrecio();
  }

  mostrarCampos() {
    this.vistaPrevia = null;
    this.imagenAnuncio = null;
    this.videoAnuncio = '';
    this.calcularPrecio();
  }
}