import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { UsuarioService } from '../../services/usuario/usuario-services';
import { Rol } from '../../models/tipo.usuario';
@Component({
  selector: 'app-registrer',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './registrer.component.html',
  styleUrl: './registrer.component.css'
})

export class Registrer {

  nombre: string = '';
  userName: string = '';
  password: string = '';
  rolUsuario: string = '';
  fechaRegistro: Date = new Date();
  errorMessage: string = '';

  constructor(private UsuarioService: UsuarioService, private router: Router) { }

  registrerUser() {
    const nuevoUsuario = {
      nombre: this.nombre,
      userName: this.userName,
      password: this.password,
      rolUsuario: this.rolUsuario,
      fechaRegistro: this.fechaRegistro.toISOString().split('T')[0]
    };

    this.UsuarioService.crearUsuario(nuevoUsuario).subscribe({
      next: (response) => {
        if (response.status === 201) {
          alert('Usuario creado con éxito');
          this.router.navigate(['/login']);
        }
      },
      error: (err) => {
        if (err.status === 400) {
          this.errorMessage = 'Datos inválidos.';
        } else if (err.status === 409) {
          this.errorMessage = 'El usuario ya existe.';
        } else {
          this.errorMessage = 'Error desconocido.';
        }
      }
    });
  }

}