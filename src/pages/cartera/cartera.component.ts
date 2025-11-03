import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UsuarioService } from '../../services/usuario/usuario-services';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuarioComponent } from '../usuario/usuario.component';
import { CarteraUsuario } from '../../services/cartera/cartera-usuario';
import { ActivatedRoute } from '@angular/router';
import { SessionService } from '../../services/session-service/session-service';

@Component({
  selector: 'app-cartera',
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './cartera.component.html',
  styleUrl: './cartera.component.css',
})
export class CarteraComponent implements OnInit {
  usuario: any = {};
  cantidadDinero: number = 0;
  saldo: number = 0;
  errorMessage: string = '';
  messageExito: string = '';
  rolUsuarioRuta: string = '/';

  constructor(
    private route: ActivatedRoute,
    private usuarioService: UsuarioService,
    private carteraService: CarteraUsuario,
    private sessionService: SessionService,
    private router: Router
  ) {
    const rol = this.sessionService.obtenerRol();
    switch (rol) {
      case 'USUARIO_COMUN': this.rolUsuarioRuta = '/usuario-comun'; break;
      case 'ANUNCIANTE': this.rolUsuarioRuta = '/anunciante'; break;
      case 'ADMIN_CINE': this.rolUsuarioRuta = '/admin-cine'; break;
      case 'ADMIN_SISTEMA': this.rolUsuarioRuta = '/admin-sistema'; break;
      default: this.rolUsuarioRuta = '/';
    }
  }

  ngOnInit() {
    this.cargarUsuario();
  }

  cargarUsuario() {
    const usuario = this.sessionService.obtenerUser();
    if (!usuario) {
      this.errorMessage = 'No se ha iniciado sesión';
      this.router.navigate(['/login']);
      return;
    }

    this.carteraService.obtenerSaldoCartera(usuario.userName).subscribe({
      next: (res) => {
        this.usuario = res;
        this.saldo = res.saldo;
      },
      error: (err) => {
        this.errorMessage = 'No se pudo cargar el saldo';
        console.error(err);
      }
    });
  }

  agregarDinero() {
    if (this.cantidadDinero <= 0) {
      this.errorMessage = 'El dinero agregado tiene que ser mayor a cero';
      return;
    }

    this.usuarioService.recargarCartera(this.cantidadDinero).subscribe({
      next: (res: any) => {
        this.messageExito = res.message;
        this.cargarUsuario();
      },
      error: (err) => {
        this.errorMessage = err.error?.error || 'Error inesperado';
      }
    });
  }

  regresar() {
    this.router.navigate([this.rolUsuarioRuta]);
  }

}