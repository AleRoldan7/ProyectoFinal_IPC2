import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CineServices {
  
  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/cine';

  constructor(private http: HttpClient) {}

  crearCine(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }
}
