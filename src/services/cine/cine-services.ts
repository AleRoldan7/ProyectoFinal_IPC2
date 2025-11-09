import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionService } from '../session-service/session-service';

@Injectable({
  providedIn: 'root',
})
export class CineServices {

  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/cine';

  constructor(private http: HttpClient, private session: SessionService) { }

  crearCine(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }

  actualizarCine(idCine: number, data: any): Observable<any> {
    const requestData = {
      ...data,
      idUsuario: this.session.obtenerIdUsuario()
    };
    return this.http.put(`${this.apiUrl}/${idCine}`, requestData);
  }

  recargarCartera(idCine: number, monto: number): Observable<any> {
    const idUsuario = this.session.obtenerIdUsuario();

    console.log('CineServices - ID Usuario obtenido:', idUsuario);
    console.log('CineServices - Tipo de ID:', typeof idUsuario);

    const requestData = {
      monto: monto,
      idUsuario: idUsuario
    };

    console.log('CineServices - Datos a enviar:', requestData);

    return this.http.post(
      `${this.apiUrl}/${idCine}/recargar-cartera`,
      requestData
    );
  }


  bloquearAnuncios(idCine: number, dias: number): Observable<any> {
    const requestData = {
      dias: dias,
      idUsuario: this.session.obtenerIdUsuario()
    };
    return this.http.post(
      `${this.apiUrl}/${idCine}/bloquear-anuncios`,
      requestData
    );
  }

  obtenerInfoCine(idCine: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${idCine}/info`);
  }
}
