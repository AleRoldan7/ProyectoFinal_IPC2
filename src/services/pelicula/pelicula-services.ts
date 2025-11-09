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

  crearPeliculaConDatos(data: any, archivo: File): Observable<any> {
    const formData = new FormData();

    formData.append('tituloPelicula', data.tituloPelicula);
    formData.append('sinopsisPelicula', data.sinopsisPelicula || '');
    formData.append('duracionPelicula', data.duracionPelicula);
    formData.append('castPelicula', data.castPelicula || '');
    formData.append('directorPelicula', data.directorPelicula || '');
    formData.append('posterPelicula', archivo);

    return this.http.post(`${this.apiUrl}/agregar`, formData);
  }

  obtenerPelicula(idPelicula: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${idPelicula}`);
  }

  obtenerTodasLasPeliculas(): Observable<any> {
    return this.http.get(`${this.apiUrl}/listar`);
  }

  buscarPeliculas(titulo: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/buscar/${titulo}`);
  }

  actualizarPelicula(data: any, archivo: File | null): Observable<any> {
    const formData = new FormData();

    formData.append('idPelicula', data.idPelicula.toString());
    formData.append('tituloPelicula', data.tituloPelicula);
    formData.append('sinopsisPelicula', data.sinopsisPelicula || '');
    formData.append('duracionPelicula', data.duracionPelicula);
    formData.append('castPelicula', data.castPelicula || '');
    formData.append('directorPelicula', data.directorPelicula || '');

    if (archivo) {
      formData.append('posterPelicula', archivo);
    }

    return this.http.put(`${this.apiUrl}/actualizar-pelicula`, formData);
  }

  eliminarPelicula(idPelicula: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${idPelicula}`);
  }

}
