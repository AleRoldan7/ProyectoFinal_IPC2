import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "../components/header/header.component";
import { SessionService } from '../services/session-service/session-service';
import { VistaAnunciosCineComponent } from "../components/anuncios/vista-cine/vista-anuncios-cine.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, VistaAnunciosCineComponent, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('Proyecto-Cines-UI');

  constructor(public sessionService: SessionService) {}

  ngOnInit(): void {
      
    if (!this.sessionService.sessionActivate) {
      console.log('No hay sesion iniciada');
    }
  }
}
