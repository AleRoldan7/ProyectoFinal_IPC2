import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { SessionService } from '../../services/session-service/session-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-usuario-comun',
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './usuario-comun.component.html',
  styleUrl: './usuario-comun.component.css',
})
export class UsuarioComunComponent {
  usuario: any = null;

  constructor(private sessionService: SessionService, private router: Router) {}

  ngOnInit(): void {
    this.cargarUsuario();
  }

  cargarUsuario() {
    this.usuario = this.sessionService.obtenerUser();
  }

  cerrarSesion() {
    if (confirm('¿Estás seguro de cerrar sesión?')) {
      this.sessionService.closeSession();
      this.usuario = null;
      this.router.navigate(['/login']).then(() => {
        window.location.reload(); 
      });
    }
  }
}
