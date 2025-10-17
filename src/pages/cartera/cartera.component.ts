import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UsuarioService } from '../../services/usuario/usuario-services';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cartera',
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './cartera.component.html',
  styleUrl: './cartera.component.css',
})
export class CarteraComponent {

  cantidadDinero: number = 0;
  errorMessage: string = '';
  messageExito: string = '';
  rolUsuarioRuta: string = '/';

  constructor(private UsuarioService: UsuarioService, private router: Router) {
    const rol = sessionStorage.getItem('rolUsuario') || '';
    switch (rol) {
      case 'USUARIO_COMUN':
        this.rolUsuarioRuta = '/usuario-comun';
        break;
      case 'ANUNCIANTE':
        this.rolUsuarioRuta = '/anunciante';
        break;
      case 'ADMIN_CINE':
        this.rolUsuarioRuta = '/admin-cine';
        break;
      case 'ADMIN_SISTEMA':
        this.rolUsuarioRuta = '/admin-sistema';
        break;
      default:
        this.rolUsuarioRuta = '/'; 
    }
  }



  agregarDinero() {
    console.log('se presiono', this.cantidadDinero);
    if (this.cantidadDinero <= 0) {
      this.errorMessage = 'El dinero agregado tiene que ser mayor a cero';
      return;
    }

    this.UsuarioService.recargarCartera(this.cantidadDinero).subscribe({
      next: (response) => {
        this.messageExito = response.message;
      },
      error: (error) => {
        if (error.status === 400) {
          this.errorMessage = error.error.error;
        } else {
          this.errorMessage = 'Error inesperado';
        }
      }
    });
  }

}
