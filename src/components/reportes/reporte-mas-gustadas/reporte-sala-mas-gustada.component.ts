import { Component, OnInit } from '@angular/core';
import { ReporteService } from '../../../services/reportes/reporte-service';
import { SessionService } from '../../../services/session-service/session-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reporte-sala-mas-gustada',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-sala-mas-gustada.component.html',
  styleUrl: './reporte-sala-mas-gustada.component.css'
})
export class ReporteSalaMasGustadaComponent implements OnInit {

  salas: any[] = [];
  fechaInicio: string = '';
  fechaFin: string = '';
  idSala?: number;
  idCine!: number;
  nombreCine: string = '';
  rolUsuario: string = '';

  constructor(
    private reporteService: ReporteService,
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

    this.generarReporte(); // Carga automática
  }

  generarReporte(): void {
    const request: any = {
      idCine: this.idCine,
      idSala: this.idSala || null,
      fechaInicio: this.fechaInicio || null,
      fechaFin: this.fechaFin || null
    };

    this.reporteService.obtenerSalasGustadas(request).subscribe({
      next: (data) => this.salas = data,
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
    if (this.salas.length === 0) {
      alert('No hay datos para generar PDF');
      return;
    }

    const request: any = {
      idCine: this.idCine,
      idSala: this.idSala || null,
      fechaInicio: this.fechaInicio || null,
      fechaFin: this.fechaFin || null
    };

    this.reporteService.generarSalasGustadasPDF(request).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `Top_Salas_Gustadas_${this.nombreCine.replace(/ /g, '_')}_${new Date().toISOString().slice(0, 10)}.pdf`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
      },
      error: async (err: any) => {
        let mensaje = 'Error desconocido';

        if (err.error instanceof Blob) {
          try {
            const text = await err.error.text();
            mensaje = text.includes('Error') ? text : 'Error PDF (Blob)';
          } catch {
            mensaje = 'Error PDF (Blob no legible)';
          }
        } else if (err.error?.error) {
          mensaje = err.error.error;
        } else if (err.status === 500) {
          mensaje = 'Error del servidor al generar PDF';
        }

        alert('Error al generar PDF: ' + mensaje);
      }
    });
  }

}
