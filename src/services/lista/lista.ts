import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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
}
