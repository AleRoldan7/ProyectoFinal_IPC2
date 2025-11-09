import { Component, OnInit } from '@angular/core';
import { CineServices } from '../../../services/cine/cine-services';
import { SessionService } from '../../../services/session-service/session-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-bloquear',
  imports: [FormsModule, CommonModule],
  templateUrl: './bloquear.component.html',
  styleUrl: './bloquear.component.css'
})
export class BloquearComponent implements OnInit {

  idCine!: number;
  saldo = 0;
  costoPorDia = 5;
  dias = 0;
  total = 0;
  bloqueoActivo = false;
  fechaFin = '';

  mostrarRecarga = false;
  montoRecarga = 0;

  constructor(
    private cineService: CineServices,
    private session: SessionService
  ) { }

  ngOnInit() {
    this.idCine = this.session.obtenerIdCine();
    const usuario = this.session.obtenerUser();
    console.log('=== DEBUG USUARIO COMPLETO ===');
    console.log('Usuario:', usuario);
    console.log('Propiedades del usuario:', Object.keys(usuario));
    console.log('idUsuario:', usuario?.idUsuario);
    console.log('ID Usuario desde método:', this.session.obtenerIdUsuario());
    this.cargarInfo();
  }

  cargarInfo() {
    this.cineService.obtenerInfoCine(this.idCine!).subscribe({
      next: (data: any) => {
        console.log('Datos del cine:', data);
        this.saldo = data.saldo_cartera || 0;
        this.costoPorDia = data.costo_por_dia || 5;
        this.bloqueoActivo = data.bloqueo_activo || false;
        this.fechaFin = data.fecha_fin || '';
        this.calcularTotal();
      },
      error: (e) => {
        console.error('Error cargando info:', e);
        this.saldo = 0;
        this.costoPorDia = 5;
        this.bloqueoActivo = false;
      }
    });
  }

  calcularTotal() {
    this.total = this.costoPorDia * this.dias;
  }

  recargar() {
    if (this.montoRecarga <= 0) return alert('Monto inválido');

    console.log('ID Cine:', this.idCine);
    console.log('ID Usuario:', this.session.obtenerIdUsuario());
    console.log('Monto:', this.montoRecarga);

    this.cineService.recargarCartera(this.idCine!, this.montoRecarga).subscribe({
      next: (response) => {
        console.log('Recarga exitosa:', response);
        alert('Cartera recargada');
        this.mostrarRecarga = false;
        this.montoRecarga = 0;
        this.cargarInfo();
      },
      error: (e) => {
        console.error('Error en recarga:', e);
        alert(e.error?.error || 'Error al recargar');
      }
    });
  }

  bloquear() {
    if (this.dias <= 0) return alert('Días inválidos');
    if (this.saldo < this.total) return alert('Saldo insuficiente');

    console.log('Bloqueando:', {
      idCine: this.idCine,
      idUsuario: this.session.obtenerIdUsuario(),
      dias: this.dias,
      total: this.total
    });

    this.cineService.bloquearAnuncios(this.idCine!, this.dias).subscribe({
      next: (response) => {
        console.log('Bloqueo exitoso:', response);
        alert(`Anuncios bloqueados por ${this.dias} días`);
        this.dias = 0;
        this.cargarInfo();
      },
      error: (e) => {
        console.error('Error en bloqueo:', e);
        alert(e.error?.error || 'Error al bloquear anuncios');
      }
    });
  }
}