import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarteraUsuario {


  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/users';

  constructor(private http: HttpClient) {}

  obtenerSaldoCartera(userName: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/cartera-usuario/${userName}`);
  }

}
