import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SalaServices {

  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/sala';

  constructor(private http: HttpClient) { }

  crearSala(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear-sala`, data);
  }

  actualizarSala(data: any): Observable<any> {
    const formData = new FormData();

    formData.append('idSala', data.idSala.toString());
    formData.append('nombreSala', data.nombreSala);
    formData.append('filaAsiento', data.filaSala.toString());
    formData.append('columnaAsiento', data.columnaSala.toString());
    formData.append('fechaCreacion', data.fechaCreacion);

    return this.http.put(`${this.apiUrl}/actualizar-sala`, formData);
  }

  obtenerSala(idSala: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${idSala}`);
  }

  obtenerSalasPorCine(idCine: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/cine/${idCine}`);
  }
}
