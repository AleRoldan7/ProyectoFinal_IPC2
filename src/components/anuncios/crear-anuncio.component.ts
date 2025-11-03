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

  constructor(private anuncioService: AnuncioServices, private sessionService: SessionService) { }

  fileSeleccionado(event: any) {
    this.imagenAnuncio = event.target.files[0];
  }

  calcularPrecio() {
    let precioBase = 0;
    switch (this.anuncioSeleccionado) {
      case tipoAnuncio.TEXTO:
        precioBase = 10;
        break;
      case tipoAnuncio.TEXTO_IMAGEN:
        precioBase = 20;
        break;
      case tipoAnuncio.TEXTO_VIDEO:
        precioBase = 30;
        break;
    }
    this.precio = precioBase * this.diasVigente;
  }

  crearAnuncio() {
    const usuario = this.sessionService.obtenerUser();
    if (!usuario) {
      alert('Debes iniciar sesión para crear anuncios');
      return;
    }

    const formData = new FormData();

    formData.append('tipoAnuncio', this.anuncioSeleccionado);
    formData.append('mensajeAnuncio', this.mensajeAnuncio);
    formData.append('nombreAnunciante', usuario.idUsuario.toString());
    formData.append('fechaInicio', this.fechaInicio);
    formData.append('diasVigente', this.diasVigente.toString());

    if (this.imagenAnuncio) {
      formData.append('imagenAnuncio', this.imagenAnuncio);
    }

    if (this.videoAnuncio) {
      formData.append('videoAnuncio', this.videoAnuncio);
    }


    this.anuncioService.crearAnuncio(formData).subscribe({
      next: (response) => {
        this.mensaje = 'Se creo anuncio';
        this.limpiarFormulario();
      },
      error: (e) => {
        console.log('No funciono', e);
        this.mensaje = 'No jala';
      }
    })
  }

  limpiarFormulario() {
    this.anuncioSeleccionado = tipoAnuncio.TEXTO;
    this.mensajeAnuncio = '';
    this.fechaInicio = '';
    this.diasVigente = 1;
    this.imagenAnuncio = null;
    this.videoAnuncio = '';
  }
}
