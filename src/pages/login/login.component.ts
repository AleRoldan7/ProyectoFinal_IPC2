import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { LoginServices } from '../../services/login/login-services';

@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class Login {

  userName: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private router: Router, private services: LoginServices) { }

  login() {
    this.services.mensaje().subscribe({
      next: (data) => {
        console.log(data);
        data.nombre;
      },
      error: (error) => {
        console.error('Error during login:', error);
        this.errorMessage = 'Login failed. Please check your credentials.';
      }
    });
  }
  

}



