import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cine } from '../../models/cine';
import { Sala } from '../../models/sala';

@Injectable({
  providedIn: 'root',
})
export class Lista {
  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/lista';

  constructor(private http: HttpClient) {}

  obtnerUsuarios(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  obtenerAdminCine(rolUsuario: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${"adminCine"}`);
  }

  obtenerPeliculas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${"peliculas"}`);
  }

  obtenerSalasAdmin(idUsuario: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/salas/${idUsuario}`);

  }

  obtenerSalas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${"salas"}`);
  }
  
  obtenerCines(): Observable<Cine[]> {
    return this.http.get<Cine[]>(`${this.apiUrl}/${"cine"}`);
  }

  obtenerSalaCine(idCine: number): Observable<Sala[]> {
    return this.http.get<Sala[]>(`${this.apiUrl}/salas/cine/${idCine}`);
  }

  obtenerPeliculasPorSalaYCine(idSala: number, idCine: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/peliculas/sala/${idSala}/cine/${idCine}`);
  }
}
