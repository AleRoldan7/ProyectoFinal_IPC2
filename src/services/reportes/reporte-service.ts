import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ReporteBoleto } from '../../models/reporte-models/reporte-boleto';
import { NewReporteComentarioRequest } from '../../models/reporte-models/reporte-comentario';
import { ReporteComentarioSalaResponse } from '../../models/reporte-models/reporte-response';

@Injectable({
  providedIn: 'root'
})
export class ReporteService {

  private apiURL = 'http://localhost:8080/Proyecto2_IPC2/api/v1/reportes';

  constructor(private http: HttpClient) { }

  generarReporteBoletos(data: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.apiURL}/boletos`, data);
  }

  generarPDF(data: any): Observable<Blob> {
    return this.http.post(`${this.apiURL}/pdf`, data, { responseType: 'blob' });
  }

  generarReporteComentarios(data: NewReporteComentarioRequest): Observable<ReporteComentarioSalaResponse[]> {
    return this.http.post<ReporteComentarioSalaResponse[]>(`${this.apiURL}/comentarios`, data);
  }

  generarPDFComentarios(data: NewReporteComentarioRequest): Observable<Blob> {
    return this.http.post(`${this.apiURL}/comentarios/pdf`, data, { responseType: 'blob' });
  }

  obtenerPeliculasPorSala(req: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.apiURL}/peliculas`, req);
  }

  generarPDFPeliculas(req: any): Observable<Blob> {
    return this.http.post(`${this.apiURL}/peliculas/pdf`, req, { responseType: 'blob' });
  }

  obtenerSalasCine(idCine: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiURL}/salas/${idCine}`);
  }

  obtenerSalasGustadas(req: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.apiURL}/salas-gustadas`, req);
  }

  generarSalasGustadasPDF(req: any): Observable<Blob> {
    return this.http.post(`${this.apiURL}/salas-gustadas/pdf`, req, { responseType: 'blob' });
  }


  generarReporteGanancias(data: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.apiURL}/ganancias`, data);
  }

  generarReporteAnuncios(data: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.apiURL}/anuncios`, data);
  }

  generarReporteGananciasAnunciante(data: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.apiURL}/ganancias-anunciante`, data);
  }

  generarSalasPopulares(data: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.apiURL}/salas-populares`, data);
  }

  generarSalasComentadas(data: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.apiURL}/salas-comentadas`, data);
  }

  generarPDFAnuncios(data: any): Observable<Blob> {
    return this.http.post(`${this.apiURL}/anuncios/pdf`, data, {
      responseType: 'blob'
    });
  }


}
