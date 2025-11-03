import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pelicula } from '../../models/pelicula';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PeliculaServices {

  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/pelicula';

  constructor(private http: HttpClient) { }


  crearPelicula(formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/agregar`, formData);
  }


  agregarPeliculaSala(idSala: number, idPelicula: number): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = { idSala, idPelicula };
    return this.http.post<any>(`${this.apiUrl}/pelicula-sala`, body, { headers });
  }

}
