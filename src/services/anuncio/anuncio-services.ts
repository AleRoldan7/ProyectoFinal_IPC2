import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Anuncio } from '../../models/anuncio';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnuncioServices {
  
  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/anuncio';
  private apiLista = 'http://localhost:8080/Proyecto2_IPC2/api/v1/lista';

  constructor(private http: HttpClient) {}

  crearAnuncio(formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear-anuncio`, formData);
  }

  getAnuncios(nombreAnunciante: number): Observable<Anuncio[]> {
    return this.http.get<Anuncio[]>(`${this.apiLista}/anuncios/${nombreAnunciante}`);
  }
}
