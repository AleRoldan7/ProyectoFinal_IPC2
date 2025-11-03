import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SessionService } from '../session-service/session-service';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/users';

  constructor(private http: HttpClient, private sessionService: SessionService) { }

  crearUsuario(usuario: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(`${this.apiUrl}/crearUsuario`, usuario, { headers, observe: 'response' });
  }

  recargarCartera(cantidadDinero: number): Observable<any> {
    const usuario = this.sessionService.obtenerUser();
    if (!usuario) {
      return new Observable(observer => observer.error('No hay usuario en sesión'));
    }

    const body = new HttpParams().set('cantidadRecarga', cantidadDinero.toString());

    return this.http.post(
      `${this.apiUrl}/${usuario.userName}/${usuario.rolUsuario}`,
      body.toString(),
      { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
    );
  }



  getUsuario(userName: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${userName}`);
  }

}