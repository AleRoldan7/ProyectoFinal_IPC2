import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../services/session-service/session-service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-header',
  imports: [CommonModule, RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  rol: string | null = null;

  constructor(private sessionService: SessionService) { }

  ngOnInit(): void {
    this.rol = this.sessionService.obtenerRol();
    console.log('Rol usuario', this.rol);
  }

  cerrarSesion(): void {
    
  }
}
