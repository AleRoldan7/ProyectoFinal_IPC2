import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SessionService } from '../../../services/session-service/session-service';
import { ReporteService } from '../../../services/reportes/reporte-service';
import { NewReporteComentarioRequest } from '../../../models/reporte-models/reporte-comentario';

@Component({
  selector: 'app-reporte-sala-comentario',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-sala-comentario.component.html',
  styleUrl: './reporte-sala-comentario.component.css'
})
export class ReporteSalaComentarioComponent implements OnInit {

  comentarios: any[] = [];
  fechaInicio: string = '';
  fechaFin: string = '';
  idSala?: number;
  idCine!: number;
  nombreCine: string = 'TODOS LOS CINES';
  rolUsuario: string = '';

  constructor(
    private comentariosService: ReporteService,
    private sessionService: SessionService
  ) { }

  ngOnInit(): void {
    const usuario = this.sessionService.obtenerUser();
    if (!usuario) {
      alert('Debes iniciar sesión');
      return;
    }

    this.rolUsuario = usuario.rolUsuario;

    if (this.rolUsuario !== 'ADMIN_CINE') {
      alert('Acceso denegado');
      return;
    }

    const idCine = this.sessionService.obtenerIdCine();
    if (!idCine) {
      alert('No tienes un cine asignado');
      return;
    }

    this.idCine = idCine;
    this.nombreCine = this.sessionService.obtenerNombreCine();

    this.generarReporte();
  }

  generarReporte(): void {
    const request: NewReporteComentarioRequest = {
      idCine: this.idCine,
      idSala: this.idSala || null,
      fechaInicio: this.fechaInicio || null,
      fechaFin: this.fechaFin || null
    };

    this.comentariosService.generarReporteComentarios(request).subscribe({
      next: (data) => this.comentarios = data,
      error: (err) => {
        console.error(err);
        alert('Error al cargar reporte: ' + (err.error?.error || 'Desconocido'));
      }
    });
  }

  limpiarFiltros(): void {
    this.fechaInicio = '';
    this.fechaFin = '';
    this.idSala = undefined;
    this.generarReporte();
  }

  descargarPDF(): void {
    if (this.comentarios.length === 0) {
      alert('No hay comentarios para generar PDF');
      return;
    }

    const request: NewReporteComentarioRequest = {
      idCine: this.idCine,
      idSala: this.idSala || null,
      fechaInicio: this.fechaInicio || null,
      fechaFin: this.fechaFin || null
    };

    this.comentariosService.generarPDFComentarios(request).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `Reporte_Comentarios_${new Date().toISOString().slice(0, 10)}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err) => {
        console.error(err);
        alert('Error al generar PDF: ' + (err.error || 'Desconocido'));
      }
    });
  }
}
