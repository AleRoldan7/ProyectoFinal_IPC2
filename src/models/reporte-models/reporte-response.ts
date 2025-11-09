export interface ReporteComentarioSalaResponse {
  nombreSala: string;
  comentario: string;
  calificacion: number;
  fecha: string; // ISO date string
  usuario: string;
}