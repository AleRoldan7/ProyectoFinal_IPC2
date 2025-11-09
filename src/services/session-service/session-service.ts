import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor() { }

  guardarSesion(usuario: any) {
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

  obtenerIdCine(): number {
    const usuario = this.obtenerUser();
    if (!usuario) return 0;
    return usuario.rolUsuario === 'ADMIN_CINE' ? usuario.idCine : 1;
  }

  obtenerNombreCine(): string {
    const usuario = this.obtenerUser();
    if (!usuario) return 'MI CINE';
    return usuario.nombreCine || 'MI CINE';
  }

  obtenerIdUsuario(): number {
    const usuario = this.obtenerUser();
    console.log('Usuario completo en obtenerIdUsuario:', usuario);
    console.log('idUsuario del objeto:', usuario?.idUsuario);
    return usuario ? usuario.idUsuario : 4;
  }
}
