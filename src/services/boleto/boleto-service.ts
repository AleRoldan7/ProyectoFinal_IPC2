import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Boleto } from '../../models/boleto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BoletoService {

  private urlApi = 'http://localhost:8080/Proyecto2_IPC2/api/v1/boletos';

  constructor(private http: HttpClient) { }

  comprarBoleto(boleto: Boleto): Observable<any> {
    return this.http.post(`${this.urlApi}/compra-boleto`, boleto);
  }

  obtenerAsientosOcupados(idPeliculaSala: number): Observable<{ fila: number, columna: number }[]> {
    return this.http.get<{ fila: number, columna: number }[]>(`${this.urlApi}/asientos-ocupados/${idPeliculaSala}`);
  }
}
