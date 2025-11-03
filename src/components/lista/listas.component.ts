import { Component, OnInit } from '@angular/core';
import { Lista } from '../../services/lista/lista';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listas',
  imports: [CommonModule],
  templateUrl: './listas.component.html',
  styleUrl: './listas.component.css'
})
export class ListasComponent implements OnInit {

  usuarios: any[] = [];
  rolUsuario = 'ADMIN_CINE';
  peliculas: any[] = [];
  salas: any[] = [];

  constructor(private listaService: Lista) { }

  ngOnInit(): void {
    const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');
    if (usuario && usuario.idUsuario) {
      this.listaSalasAdmin(usuario.idUsuario);
    } else {
      console.warn('No hay usuario logueado o falta idUsuario');
    }
    this.listaAdminCine();
    this.listaPeliculas();
  }


  listaAdminCine() {
    this.listaService.obtenerAdminCine(this.rolUsuario).subscribe({
      next: (admin) => {
        this.usuarios = Array.isArray(admin) ? admin : [admin];
      },
      error: (e) => {
        console.log('No jaloooo', e);
      },
    });
  }

  listaPeliculas() {
    this.listaService.obtenerPeliculas().subscribe({
      next: (pelicula) => {
        this.peliculas = Array.isArray(pelicula) ? pelicula : [pelicula];
      },
      error: (e) => {
        console.log('No jalo peliculas', e);
      }
    });
  }

  listaSalas() {
    this.listaService.obtenerSalas().subscribe({
      next: (sala) => {
        this.salas = Array.isArray(sala) ? sala : [sala];
        console.log('salas', sala);
      },
      error: (e) => {
        console.log('No jala las salas', e);
      }
    });
  }

  listaSalasAdmin(idUsuario: number) {
    this.listaService.obtenerSalasAdmin(idUsuario).subscribe({
      next: (sala) => {
        this.salas = Array.isArray(sala) ? sala : [sala];
        console.log('salas', sala);
      },
      error: (e) => {
        console.log('No jala las salas', e);
      }
    });
  }
}
