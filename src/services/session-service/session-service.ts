import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  
  constructor() {}

  guardarSesion(usuario: any){
    localStorage.setItem('usuario', JSON.stringify(usuario));
  }

  obtenerUser() {
    const userData = localStorage.getItem('usuario');
    return userData ? JSON.parse(userData) : null;
  }

  obtenerRol(): string {
    const usuario = this.obtenerUser();
    return usuario ? usuario.rolUsuario : '';
  }
  sessionActivate(): boolean {
    return !!this.obtenerUser();
  }

  closeSession() {
    localStorage.removeItem('usuario');
  }
}
