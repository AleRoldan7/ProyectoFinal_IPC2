import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "../components/header/header.component";
import { SessionService } from '../services/session-service/session-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('Proyecto-Cines-UI');

  constructor(private sessionSevice: SessionService) {}

  ngOnInit(): void {
      
    this.sessionSevice.closeSession();

    if (!this.sessionSevice.sessionActivate) {
      console.log('No hay sesion iniciada');
    }
  }
}
