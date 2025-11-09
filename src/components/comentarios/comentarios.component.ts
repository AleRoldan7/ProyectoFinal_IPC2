import { Component, Input, OnChanges, SimpleChange, SimpleChanges } from '@angular/core';
import { SessionService } from '../../services/session-service/session-service';
import { ComentarioService } from '../../services/comentario-service/comentario-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-comentarios',
  imports: [FormsModule, CommonModule],
  templateUrl: './comentarios.component.html',
  styleUrl: './comentarios.component.css'
})
export class ComentariosComponent implements OnChanges {


  @Input() pelicula: any = null; 
  @Input() sala: any = {};        

  constructor(private sessionService: SessionService, private comentarioService: ComentarioService) {}


  nuevoComentario = {
    comentario: '',
    calificacion: 5
  };

  isLoggedIn = false;
  isAdminCine = false;
  comentarios: any[] = [];

  ngOnInit(): void {
    this.isLoggedIn = this.sessionService.sessionActivate();
    this.isAdminCine = this.sessionService.obtenerRol() === 'ADMIN_CINE';
  }

  // SE EJECUTA CUANDO CAMBIA @Input()
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['pelicula'] && this.pelicula) {
      this.cargarComentarios();
    }
  }

  cargarComentarios() {
    if (!this.pelicula?.idProyeccion) return;

    this.comentarioService.obtenerComentariosPelicula(this.pelicula.idProyeccion).subscribe({
      next: (data) => this.comentarios = data,
      error: () => this.comentarios = []
    });
  }

  enviarComentario() {
    if (!this.nuevoComentario.comentario.trim()) {
      alert('Escribe un comentario');
      return;
    }

    const comentario = {
      idUsuario: this.sessionService.obtenerUser().idUsuario,
      idProyeccion: this.pelicula.idProyeccion,
      comentario: this.nuevoComentario.comentario,
      calificacion: this.nuevoComentario.calificacion
    };

    this.comentarioService.comentarPelicula(comentario).subscribe({
      next: () => {
        this.nuevoComentario.comentario = '';
        this.cargarComentarios();
      },
      error: (err) => alert('Error: ' + (err.error?.error || 'No se pudo enviar'))
    });
  }

  toggleBloqueoComentarios() {
    if (!this.sala?.id_sala_cine) return;

    this.comentarioService.bloquearComentariosSala(
      this.sala.id_sala_cine,
      !this.sala.bloqueada_comentarios
    ).subscribe({
      next: () => {
        this.sala.bloqueada_comentarios = !this.sala.bloqueada_comentarios;
      }
    });
  }

  toggleVisibilidad() {
    if (!this.sala?.id_sala_cine) return;

    this.comentarioService.bloquearVisibilidadSala(
      this.sala.id_sala_cine,
      !this.sala.bloqueada_visibilidad
    ).subscribe({
      next: () => {
        this.sala.bloqueada_visibilidad = !this.sala.bloqueada_visibilidad;
      }
    });
  }
}