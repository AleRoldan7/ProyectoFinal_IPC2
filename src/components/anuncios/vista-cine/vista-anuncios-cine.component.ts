import { Component, OnDestroy, OnInit } from '@angular/core';
import { Anuncio } from '../../../models/anuncio';
import { AnuncioServices } from '../../../services/anuncio/anuncio-services';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-vista-anuncios-cine',
  imports: [FormsModule, CommonModule],
  templateUrl: './vista-anuncios-cine.component.html',
  styleUrl: './vista-anuncios-cine.component.css'
})
export class VistaAnunciosCineComponent implements OnInit {

 anuncios: any[] = [];
  anuncioIzquierdo: any;
  anuncioDerecho: any;
  private intervalId: any;

  constructor(
    private anuncioService: AnuncioServices,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.cargarAnuncios();
    this.intervalId = setInterval(() => this.cambiarAnuncios(), 8000);
  }

  cargarAnuncios() {
    this.anuncioService.obtenerAnunciosVisibles().subscribe({
      next: (data) => {
        console.log('ANUNCIOS VISIBLES:', data);
        this.anuncios = data.filter(a => !this.estaBloqueado(a));
        this.asignarAnunciosIniciales();
      },
      error: (err) => console.error('ERROR:', err)
    });
  }

  asignarAnunciosIniciales() {
    if (this.anuncios.length > 0) {
      this.anuncioIzquierdo = this.anuncios[0];
      this.anuncioDerecho = this.anuncios[1] || this.anuncios[0];
    }
  }

  cambiarAnuncios() {
    if (this.anuncios.length < 2) return;
    const actualIzq = this.anuncios.indexOf(this.anuncioIzquierdo);
    const nextIzq = (actualIzq + 1) % this.anuncios.length;
    const nextDer = (nextIzq + 1) % this.anuncios.length;
    this.anuncioIzquierdo = this.anuncios[nextIzq];
    this.anuncioDerecho = this.anuncios[nextDer];
  }

  estaBloqueado(anuncio: any): boolean {
    return false;
  }

  getYouTubeEmbedUrl(videoUrl: string): SafeResourceUrl {
    if (!videoUrl) return '';
    const videoId = this.extractYouTubeId(videoUrl);
    if (!videoId) return '';
    return this.sanitizer.bypassSecurityTrustResourceUrl(
      `https://www.youtube.com/embed/${videoId}?autoplay=1&mute=1&loop=1&playlist=${videoId}&rel=0&modestbranding=1`
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

  getImagenUrl(idAnuncio: number): string {
    return `http://localhost:8080/Proyecto2_IPC2/api/v1/anuncio/imagen/${idAnuncio}`;
  }
}
