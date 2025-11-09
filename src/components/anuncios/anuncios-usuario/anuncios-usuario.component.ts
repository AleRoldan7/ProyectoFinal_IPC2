import { Component, OnInit } from '@angular/core';
import { AnuncioServices } from '../../../services/anuncio/anuncio-services';
import { SessionService } from '../../../services/session-service/session-service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-anuncios-usuario',
  imports: [CommonModule, RouterLink],
  templateUrl: './anuncios-usuario.component.html',
  styleUrl: './anuncios-usuario.component.css',
})
export class AnunciosUsuarioComponent implements OnInit {
  anuncios: any[] = [];
  usuario: any;

  constructor(
    private anuncioService: AnuncioServices,
    private sessionService: SessionService,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    this.usuario = this.sessionService.obtenerUser();
    if (this.usuario?.idUsuario) {
      this.cargarAnuncios();
    }
  }

  cargarAnuncios() {
    this.anuncioService.getAnuncios(this.usuario.idUsuario).subscribe({
      next: (data) => {
        console.log('ANUNCIOS CARGADOS:', data); 
        this.anuncios = data;
        this.cargarImagenes(); 
      },
      error: (e) => {
        console.error('Error:', e);
      }
    });
  }

  cargarImagenes() {
    this.anuncios.forEach(anuncio => {
      if (anuncio.tipoAnuncio === 'TEXTO_IMAGEN' && anuncio.idAnuncio) {
        console.log('Cargando imagen para idAnuncio:', anuncio.idAnuncio);

        this.anuncioService.getImagenAnuncio(anuncio.idAnuncio).subscribe({
          next: (blob) => {
            console.log('Blob recibido, tamaño:', blob.size);
            if (blob.size > 0) {
              const url = URL.createObjectURL(blob);
              anuncio.imagenUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
              console.log('Imagen asignada:', url);
            } else {
              anuncio.imagenUrl = this.getPlaceholder();
            }
          },
          error: (err) => {
            console.error('Error al cargar imagen ID', anuncio.idAnuncio, err);
            anuncio.imagenUrl = this.getPlaceholder();
          }
        });
      }
    });
  }

  private getPlaceholder(): SafeResourceUrl {
    const svg = `
      <svg width="300" height="200" xmlns="http://www.w3.org/2000/svg">
        <rect width="100%" height="100%" fill="#f8f9fa"/>
        <text x="50%" y="50%" font-size="18" text-anchor="middle" fill="#6c757d" dy=".3em">
          Sin Imagen
        </text>
      </svg>`;
    const blob = new Blob([svg], { type: 'image/svg+xml' });
    const url = URL.createObjectURL(blob);
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  getYouTubeEmbedUrl(videoUrl: string): SafeResourceUrl {
    if (!videoUrl) return '';
    const videoId = this.extractYouTubeId(videoUrl);
    if (!videoId) return '';
    return this.sanitizer.bypassSecurityTrustResourceUrl(
      `https://www.youtube.com/embed/${videoId}?rel=0`
    );
  }


  extractYouTubeId(url: string): string | null {
    if (!url) return null;

    const regex = [
      /(?:youtube\.com\/watch\?v=|youtu\.be\/|youtube\.com\/embed\/)([^&\n?#]+)/, 
      /youtube\.com\/v\/([^&\n?#]+)/,
      /youtube\.com\/shorts\/([^&\n?#]+)/
    ];

    for (const r of regex) {
      const match = url.match(r);
      if (match) return match[1];
    }
    return null;
  }
  esActivo(anuncio: any): boolean {
    const inicio = new Date(anuncio.fechaInicio[0], anuncio.fechaInicio[1] - 1, anuncio.fechaInicio[2]);
    const fin = new Date(inicio);
    fin.setDate(inicio.getDate() + anuncio.diasVigencia);
    return new Date() <= fin && new Date() >= inicio;
  }
}