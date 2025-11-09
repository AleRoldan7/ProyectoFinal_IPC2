import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ComentarioService {

  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/comentarios';

  constructor(private http: HttpClient) {}

  comentarPelicula(comentario: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/pelicula`, comentario);
  }

  comentarSala(comentario: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/sala`, comentario);
  }

  bloquearComentariosSala(idSalaCine: number, bloquear: boolean): Observable<any> {
    return this.http.put(`${this.apiUrl}/sala/comentarios/${idSalaCine}?bloquear=${bloquear}`, {});
  }

  bloquearVisibilidadSala(idSalaCine: number, bloquear: boolean): Observable<any> {
    return this.http.put(`${this.apiUrl}/sala/visibilidad/${idSalaCine}?bloquear=${bloquear}`, {});
  }

  obtenerComentariosPelicula(idProyeccion: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/pelicula/${idProyeccion}`);
  }

  obtenerComentariosSala(idSalaCine: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/sala/${idSalaCine}`);
  }
}
