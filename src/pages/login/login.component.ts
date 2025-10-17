import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { LoginServices } from '../../services/login/login-services';
import { Rol } from '../../models/tipo.usuario';

@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class Login {
  userName: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private router: Router, private loginService: LoginServices) { }

  login() {
    const loginUsuario = {
      userName: this.userName,
      password: this.password,
    };

    this.loginService.loginUsuario(loginUsuario).subscribe({
      next: (response) => {
        console.log('Si entro:', response);

        sessionStorage.setItem('userName', response.userName);
        sessionStorage.setItem('rolUsuario', response.rolUsuario);
        
        switch (response.rolUsuario) {
          case 'USUARIO_COMUN':
            this.router.navigate(['/usuario-comun']);
            break;
          case 'ANUNCIANTE':
            this.router.navigate(['/anunciante']);
            break;
          case 'ADMIN_CINE':
            this.router.navigate(['/admin-cine']);
            break;
          case 'ADMIN_SISTEMA':
            this.router.navigate(['/admin-sistema']);
            break;
          default:
            this.errorMessage = 'Rol desconocido';
            console.log('usuario: ', response.rolUsuario);
        }
      },
      error: (err) => {
        this.errorMessage = 'Credenciales incorrectas';
      },
    });
  }
}
