import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class LoginServices {
  
  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/login';
  constructor(private http: HttpClient) { 

  }

  public mensaje(): Observable<Usuario> {
    return this.http.get<Usuario>(this.apiUrl);
  }
}
