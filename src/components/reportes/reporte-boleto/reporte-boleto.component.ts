import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReporteBoleto } from '../../../models/reporte-models/reporte-boleto';
import { ReporteService } from '../../../services/reportes/reporte-service';
import { SessionService } from '../../../services/session-service/session-service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-reporte-boleto',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './reporte-boleto.component.html',
  styleUrl: './reporte-boleto.component.css'
})
export class ReporteBoletoComponent implements OnInit {

  boletos: any[] = [];
  fechaInicio: string = '';
  fechaFin: string = '';
  idSala?: number;
  idCine?: number;
  totalVenta: number = 0;
  nombreCine: string = 'TODOS LOS CINES';
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

    this.rolUsuario = usuario.rolUsuario || '';

    if (this.rolUsuario === 'ADMIN_CINE' && usuario.idCine) {
      this.idCine = usuario.idCine;
      this.nombreCine = usuario.nombreCine || 'MI CINE';
    }

    // CARGAR REPORTE AUTOMÁTICO
    this.generarReporteBoleto();
  }

  generarReporteBoleto(): void {
    const usuario = this.sessionService.obtenerUser();
    if (!usuario) return;

    const request = {
      fechaInicio: this.fechaInicio || null,
      fechaFin: this.fechaFin || null,
      idSala: this.idSala || null,
      idCine: this.idCine || null,
      usuarioResponse: {
        idUsuario: usuario.idUsuario,
        rolUsuario: usuario.rolUsuario
      }
    };

    this.reporteService.generarReporteBoletos(request).subscribe({
      next: (data) => {
        this.boletos = data;
        this.totalVenta = data.reduce((acc: number, b: any) => acc + b.precioBoleto, 0);
        if (data.length > 0 && !this.idCine) {
          this.nombreCine = data[0].nombreCine;
        }
      },
      error: (err) => {
        console.error(err);
        alert('Error al cargar reporte');
      }
    });
  }

  limpiarFiltros(): void {
    this.fechaInicio = '';
    this.fechaFin = '';
    this.idSala = undefined;
    this.generarReporteBoleto();
  }

  descargarPDF(): void {
    const usuario = this.sessionService.obtenerUser();
    if (!usuario) return;

    const request = {
      fechaInicio: this.fechaInicio || null,
      fechaFin: this.fechaFin || null,
      idSala: this.idSala || null,
      idCine: this.idCine || null,
      usuarioResponse: {
        idUsuario: usuario.idUsuario,
        rolUsuario: usuario.rolUsuario
      }
    };

    this.reporteService.generarPDF(request).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `Reporte_Boletos_${this.nombreCine.replace(/\s+/g, '_')}_${new Date().toISOString().slice(0,10)}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => alert('Error al generar PDF')
    });
  }
}
