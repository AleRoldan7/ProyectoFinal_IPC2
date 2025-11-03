import { Component, OnInit } from '@angular/core';
import { AnuncioServices } from '../../../services/anuncio/anuncio-services';
import { SessionService } from '../../../services/session-service/session-service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-anuncios-usuario',
  imports: [CommonModule,  FormsModule],
  templateUrl: './anuncios-usuario.component.html',
  styleUrl: './anuncios-usuario.component.css'
})
export class AnunciosUsuarioComponent implements OnInit {

  anuncios: any[] = [];
  usuario: any;

  constructor(private anuncioService: AnuncioServices, private sessionService: SessionService) { }

  ngOnInit(): void {
    this.usuario = this.sessionService.obtenerUser();
    console.log('ID usuario:', this.usuario?.idUsuario);
    if (this.usuario) {
      this.anuncioService.getAnuncios(this.usuario.idUsuario).subscribe({
        next: data => {
          console.log('Anuncios recibidos:', data);
          this.anuncios = data;
        },
        error: e => console.error(e)
      });
    }
  }

  getImagenUrl(idAnuncio: number): string {
    return `http://localhost:8080/Proyecto2_IPC2/api/v1/anuncio/imagen/${this.usuario.idUsuario}`;
  }


}
