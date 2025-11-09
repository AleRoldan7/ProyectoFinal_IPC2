import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Anuncio } from '../../models/anuncio';
import { Observable, throwError } from 'rxjs';
import { SessionService } from '../session-service/session-service';

@Injectable({
  providedIn: 'root',
})
export class AnuncioServices {
  private apiUrl = 'http://localhost:8080/Proyecto2_IPC2/api/v1/anuncio';
  private apiLista = 'http://localhost:8080/Proyecto2_IPC2/api/v1/lista';

  constructor(private http: HttpClient, private sessionService: SessionService) { }

  crearAnuncio(formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear-anuncio`, formData);
  }

  getAnuncios(idUsuario: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/lista/anuncios/${idUsuario}`);
  }

  getImagenAnuncio(idAnuncio: number): Observable<Blob> {
    const url = `http://localhost:8080/Proyecto2_IPC2/api/v1/anuncio/imagen/${idAnuncio}`;
    console.log('Solicitando imagen:', url);
    return this.http.get(url, { responseType: 'blob' });
  }

  obtenerAnunciosActivos(): Observable<Anuncio[]> {
    return this.http.get<Anuncio[]>(`${this.apiUrl}/activos`);
  }

  estaBloqueado(anuncio: Anuncio): boolean {
    const bloqueados = JSON.parse(localStorage.getItem('anunciosBloqueados') || '[]');
    return bloqueados.includes(anuncio.idAnuncio);
  }

  obtenerAnunciosVisibles(): Observable<Anuncio[]> {
    return this.http.get<Anuncio[]>(`${this.apiUrl}/visibles`);
  }

  toggleActivo(id: number, activo: boolean): Observable<any> {
    const user = this.sessionService.obtenerUser();
    if (!user?.idUsuario) {
      return throwError(() => new Error('Usuario no autenticado'));
    }
    return this.http.put(`${this.apiUrl}/toggle-activo/${id}?activo=${activo}`, {}, {
      headers: { idUsuario: user.idUsuario.toString() }
    });
  }

  

}
