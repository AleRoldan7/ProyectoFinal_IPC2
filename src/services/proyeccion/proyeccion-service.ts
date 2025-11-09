import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProyeccionService {
  
  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/proyectar'; 

  constructor(private http: HttpClient) { }

  crearProyeccion(proyeccion: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/asignar-pelicula`, proyeccion);
  }

}
