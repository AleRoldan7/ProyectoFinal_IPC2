import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

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

    constructor(private router: Router) {}

    registrerUser() {
        
    }

}