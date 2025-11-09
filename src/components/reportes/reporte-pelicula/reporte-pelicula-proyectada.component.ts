import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReporteService } from '../../../services/reportes/reporte-service';
import { SessionService } from '../../../services/session-service/session-service';

interface PeliculaProyectada {
  nombreSala: string;
  tituloPelicula: string;
  fecha: string;
}

interface Sala {
  idSalaCine: number;
  nombreSala: string;
}

@Component({
  selector: 'app-reporte-pelicula-proyectada',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-pelicula-proyectada.component.html',
  styleUrl: './reporte-pelicula-proyectada.component.css'
})
export class ReportePeliculaProyectadaComponent implements OnInit {

  idCine: number | null = null;
  nombreCine: string = '';
  usuarioNombre: string = '';
  fechaInicio: string = '';
  fechaFin: string = '';
  idSala: number | null = null;
  peliculas: PeliculaProyectada[] = [];
  salas: Sala[] = [];
  mostrarResultado = false;

  constructor(
    private reporteService: ReporteService,
    private sessionService: SessionService
  ) { }

  ngOnInit(): void {
    const usuario = this.sessionService.obtenerUser();
    if (!usuario || usuario.rolUsuario !== 'ADMIN_CINE') {
      alert('Acceso denegado');
      return;
    }

    this.idCine = this.sessionService.obtenerIdCine();
    this.nombreCine = this.sessionService.obtenerNombreCine();
    this.usuarioNombre = usuario.nombre;

    this.cargarSalas();
    this.setDefaultDates();
  }

  cargarSalas(): void {
    this.reporteService.obtenerSalasCine(this.idCine!).subscribe({
      next: (data) => this.salas = data,
      error: () => alert('Error al cargar salas')
    });
  }

  setDefaultDates(): void {
    const hoy = new Date();
    const inicio = new Date(hoy.getFullYear(), hoy.getMonth(), 1);
    this.fechaInicio = inicio.toISOString().split('T')[0];
    this.fechaFin = hoy.toISOString().split('T')[0];
  }

  generarReporte(): void {
    if (!this.fechaInicio || !this.fechaFin) return;

    const request = {
      idCine: this.idCine,
      idSala: this.idSala,
      fechaInicio: this.fechaInicio,
      fechaFin: this.fechaFin
    };

    this.reporteService.obtenerPeliculasPorSala(request).subscribe({
      next: (data) => {
        this.peliculas = data;
        this.mostrarResultado = true;
      },
      error: () => alert('Error al generar reporte')
    });
  }

  descargarPDF(): void {
    const request = {
      idCine: this.idCine,
      idSala: this.idSala,
      fechaInicio: this.fechaInicio,
      fechaFin: this.fechaFin
    };

    this.reporteService.generarPDFPeliculas(request).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `Peliculas_${this.fechaInicio}_al_${this.fechaFin}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => alert('Error al generar PDF')
    });
  }
}
