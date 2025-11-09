export interface NewReporteComentarioRequest {
  idCine: number;          
  idSala?: number | null;
  fechaInicio?: string | null;
  fechaFin?: string | null;
}