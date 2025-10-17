import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/users';

  constructor(private http: HttpClient) { }

  crearUsuario(usuario: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.apiUrl, usuario, { headers, observe: 'response' });
  }

  recargarCartera(cantidadDinero: number): Observable<any> {
    const userName = sessionStorage.getItem('userName') || '';
    const rolUsuario = sessionStorage.getItem('rolUsuario') || '';

    if (!userName || !rolUsuario) {
      console.error('No hay usuario en sesión');
      return new Observable(observer => observer.error('No hay usuario en sesión'));
    }

    const body = new HttpParams().set('cantidadRecarga', cantidadDinero.toString());

    return this.http.post(
      `http://localhost:8080/Proyecto2_IPC2/api/v1/users/${userName}/${rolUsuario}`,
      body.toString(),
      { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
    );
  }


}