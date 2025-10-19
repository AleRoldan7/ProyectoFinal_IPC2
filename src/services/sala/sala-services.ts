import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SalaServices {
  
  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/sala';

  constructor(private http: HttpClient) {}

  crearSala(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }
}
