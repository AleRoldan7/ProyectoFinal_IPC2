// src/app/components/reporte-sistema/reporte-sistema.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ReporteService } from '../../../services/reportes/reporte-service';
import { Observable } from 'rxjs';

interface ReporteRequest {
  fechaInicio: string;
  fechaFin: string;
  tipoReporte?: string;
  tipoAnuncio?: string;
  periodo?: string;
  idAnunciante?: number | null;
  idCine?: number | null;
  [key: string]: any;
}

@Component({
  selector: 'app-reporte-sistema',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './reporte-sistema.component.html',
  styleUrls: ['./reporte-sistema.component.css']
})
export class ReporteSistemaComponent implements OnInit {
  fechaInicio: string = '';
  fechaFin: string = '';
  tipoReporte: string = 'ganancias';
  tipoAnuncio: string = '';
  periodo: string = '';
  idAnunciante: number | null = null;
  idCine: number | null = null;

  reporteGanancias: any[] = [];
  reporteAnuncios: any[] = [];
  reporteGananciasAnunciante: any[] = [];
  salasPopulares: any[] = [];
  salasComentadas: any[] = [];

  isLoading: boolean = false;
  nombreCine: string = 'SISTEMA';
  rolUsuario: string = 'ADMIN_SISTEMA';

  totalGanancias: number = 0;
  totalIngresos: number = 0;
  totalCostos: number = 0;

  constructor(private reporteService: ReporteService) { }

  ngOnInit(): void {
    this.establecerFechasPorDefecto();
  }

  establecerFechasPorDefecto(): void {
    const hoy = new Date();
    const primerDiaMes = new Date(hoy.getFullYear(), hoy.getMonth(), 1);

    this.fechaInicio = primerDiaMes.toISOString().split('T')[0];
    this.fechaFin = hoy.toISOString().split('T')[0];
  }

  generarReporte(): void {
    if (!this.fechaInicio || !this.fechaFin) {
      alert('Por favor seleccione ambas fechas');
      return;
    }

    this.isLoading = true;

    switch (this.tipoReporte) {
      case 'ganancias':
        this.generarReporteGanancias();
        break;
      case 'anuncios':
        this.generarReporteAnuncios();
        break;
      case 'ganancias-anunciante':
        this.generarReporteGananciasAnunciante();
        break;
      case 'salas-populares':
        this.generarSalasPopulares();
        break;
      case 'salas-comentadas':
        this.generarSalasComentadas();
        break;
    }
  }

  generarReporteGanancias(): void {
    const requestData = {
      fechaInicio: this.fechaInicio,
      fechaFin: this.fechaFin
    };

    console.log('Generando reporte de ganancias:', requestData);
    this.isLoading = false;

    this.reporteService.generarReporteGanancias(requestData).subscribe({
      next: (response: any) => {
        this.reporteGanancias = response;
        this.calcularTotalesGanancias();
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error:', error);
        this.isLoading = false;
        alert('Error al generar reporte de ganancias');
      }
    });
  }

  generarReporteAnuncios(): void {
    const requestData = {
      fechaInicio: this.fechaInicio,
      fechaFin: this.fechaFin,
      tipoAnuncio: this.tipoAnuncio || null,
      periodo: this.periodo || null
    };

    console.log('Enviando datos al backend:', requestData);

    this.reporteService.generarReporteAnuncios(requestData).subscribe({
      next: (response: any) => {
        this.reporteAnuncios = response;
        this.isLoading = false;
        console.log('Reporte de anuncios recibido:', response);
      },
      error: (error: any) => {
        console.error('Error completo:', error);
        console.error('Error details:', error.error);
        this.isLoading = false;
        alert('Error al generar reporte de anuncios: ' + (error.error?.error || error.message));
      }
    });
  }

  generarReporteGananciasAnunciante(): void {
    const requestData = {
      fechaInicio: this.fechaInicio,
      fechaFin: this.fechaFin,
      idAnunciante: this.idAnunciante
    };

    console.log('Generando reporte de ganancias por anunciante:', requestData);
    this.isLoading = false;

    this.reporteService.generarReporteGananciasAnunciante(requestData).subscribe({
      next: (response: any) => {
        this.reporteGananciasAnunciante = response;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error:', error);
        this.isLoading = false;
        alert('Error al generar reporte de ganancias por anunciante');
      }
    });
  }

  generarSalasPopulares(): void {
    const requestData = {
      fechaInicio: this.fechaInicio,
      fechaFin: this.fechaFin,
      idCine: this.idCine
    };

    console.log('Generando reporte de salas populares:', requestData);
    this.isLoading = false;

    this.reporteService.generarSalasPopulares(requestData).subscribe({
      next: (response: any) => {
        this.salasPopulares = response;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error:', error);
        this.isLoading = false;
        alert('Error al generar reporte de salas populares');
      }
    });
  }

  generarSalasComentadas(): void {
    const requestData = {
      fechaInicio: this.fechaInicio,
      fechaFin: this.fechaFin,
      idCine: this.idCine
    };

    console.log('Generando reporte de salas comentadas:', requestData);
    this.isLoading = false;

    this.reporteService.generarSalasComentadas(requestData).subscribe({
      next: (response: any) => {
        this.salasComentadas = response;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error:', error);
        this.isLoading = false;
        alert('Error al generar reporte de salas comentadas');
      }
    });
  }

  descargarPDF(): void {
    let requestData: any;
    let serviceCall: Observable<Blob>;

    switch (this.tipoReporte) {
      case 'anuncios':
        requestData = {
          fechaInicio: this.fechaInicio,
          fechaFin: this.fechaFin,
          tipoAnuncio: this.tipoAnuncio || null
        };
        serviceCall = this.reporteService.generarPDFAnuncios(requestData);
        break;

      case 'ganancias-anunciante':
        alert('PDF para ganancias por anunciante en desarrollo');
        return;

      case 'salas-populares':
        alert('PDF para salas populares en desarrollo');
        return;

      case 'salas-comentadas':
        alert('PDF para salas comentadas en desarrollo');
        return;

      case 'ganancias':
        alert('PDF para ganancias en desarrollo');
        return;

      default:
        alert('Tipo de reporte no soportado para PDF');
        return;
    }

    serviceCall.subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `reporte-${this.tipoReporte}-${this.fechaInicio}-${this.fechaFin}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error: any) => {
        console.error('Error:', error);
        alert('Error al generar PDF: ' + (error.error?.error || error.message));
      }
    });
  }

  limpiarFiltros(): void {
    this.fechaInicio = '';
    this.fechaFin = '';
    this.tipoAnuncio = '';
    this.periodo = '';
    this.idAnunciante = null;
    this.idCine = null;
    this.reporteGanancias = [];
    this.reporteAnuncios = [];
    this.reporteGananciasAnunciante = [];
    this.salasPopulares = [];
    this.salasComentadas = [];
    this.establecerFechasPorDefecto();
  }

  calcularTotalesGanancias(): void {
    this.totalGanancias = this.reporteGanancias.reduce((sum: number, item: any) => sum + (item.ganancia || 0), 0);
    this.totalIngresos = this.reporteGanancias.reduce((sum: number, item: any) => sum + (item.totalIngresos || 0), 0);
    this.totalCostos = this.reporteGanancias.reduce((sum: number, item: any) => sum + (item.totalCostos || 0), 0);
  }

  getTotalAnuncios(): number {
    return this.reporteAnuncios.reduce((sum: number, item: any) => sum + (item.costo || 0), 0);
  }

  getTotalGananciasAnunciante(): number {
    return this.reporteGananciasAnunciante.reduce((sum: number, item: any) => sum + (item.totalGastado || 0), 0);
  }

  tieneDatosReporte(): boolean {
    switch (this.tipoReporte) {
      case 'ganancias': return this.reporteGanancias.length > 0;
      case 'anuncios': return this.reporteAnuncios.length > 0;
      case 'ganancias-anunciante': return this.reporteGananciasAnunciante.length > 0;
      case 'salas-populares': return this.salasPopulares.length > 0;
      case 'salas-comentadas': return this.salasComentadas.length > 0;
      default: return false;
    }
  }

  getBadgeClass(tipoAnuncio: string): string {
    switch (tipoAnuncio) {
      case 'TEXTO': return 'bg-primary';
      case 'TEXTO_IMAGEN': return 'bg-success';
      case 'TEXTO_VIDEO': return 'bg-info';
      default: return 'bg-secondary';
    }
  }
}