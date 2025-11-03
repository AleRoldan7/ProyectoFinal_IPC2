import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Sala } from '../../models/sala';
import { Cine } from '../../models/cine';
import { Boleto } from '../../models/boleto';
import { Lista } from '../../services/lista/lista';
import { BoletoService } from '../../services/boleto/boleto-service';
import { SessionService } from '../../services/session-service/session-service';

@Component({
  selector: 'app-comprar-boletos',
  imports: [CommonModule, FormsModule],
  templateUrl: './comprar-boletos.component.html',
  styleUrls: ['./comprar-boletos.component.css']
})
export class ComprarBoletosComponent implements OnInit {

  cines: Cine[] = [];
  cineElegido?: Cine;
  salas: Sala[] = [];
  salaElegida?: Sala;
  peliculasDisponibles: any[] = [];
  peliculaElegida?: number;
  asientos: boolean[] = [];
  ocupados: boolean[] = [];
  asientoElegido: number[] = [];
  precioBoleto: number = 50; 
  mensaje: string = '';
  tipoMensaje: 'success' | 'danger' | '' = '';

  constructor(
    private listaService: Lista,
    private boletoService: BoletoService,
    private session: SessionService
  ) { }

  ngOnInit(): void {
    this.cargarCines();
  }

  cargarCines() {
    this.listaService.obtenerCines().subscribe({
      next: data => this.cines = data,
      error: err => this.mensajeError('Error al cargar cines: ' + err.message)
    });
  }

  cargarSalasPorCine() {
    if (!this.cineElegido) return;

    this.listaService.obtenerSalaCine(this.cineElegido.idCine).subscribe({
      next: data => {
        this.salas = data;
        this.salaElegida = undefined;
        this.peliculasDisponibles = [];
        this.peliculaElegida = undefined;
        this.inicializarAsientos();
      },
      error: err => this.mensajeError('Error al cargar salas: ' + err.message)
    });
  }

  cargarPeliculasPorSalaYCine() {
    if (!this.salaElegida || !this.cineElegido) {
      this.peliculasDisponibles = [];
      return;
    }

    this.listaService.obtenerPeliculasPorSalaYCine(this.salaElegida.idSala, this.cineElegido.idCine)
      .subscribe({
        next: data => {
          this.peliculasDisponibles = data;
          this.peliculaElegida = undefined;
          this.inicializarAsientos();
        },
        error: err => this.mensajeError('Error al cargar películas: ' + err.message)
      });
  }

  seleccionarPelicula() {
    if (!this.peliculaElegida || !this.salaElegida) return;

    this.inicializarAsientos();
    this.obtenerBoletosOcupados();
  }

  inicializarAsientos() {
    if (!this.salaElegida) return;
    const total = this.salaElegida.filaSala * this.salaElegida.columnaSala;
    this.asientos = Array(total).fill(false);
    this.ocupados = Array(total).fill(false);
    this.asientoElegido = [];
  }

  obtenerBoletosOcupados() {
    if (!this.peliculaElegida) return;

    this.boletoService.obtenerAsientosOcupados(this.peliculaElegida)
      .subscribe({
        next: data => {
          data.forEach(b => {
            const index = (b.fila - 1) * this.salaElegida!.columnaSala + (b.columna - 1);
            this.ocupados[index] = true;
          });
        },
        error: err => console.error('Error al obtener asientos ocupados:', err)
      });
  }

  toggleAsiento(index: number) {
    if (this.ocupados[index]) return;

    this.asientos[index] = !this.asientos[index];
    if (this.asientos[index]) {
      this.asientoElegido.push(index);
    } else {
      this.asientoElegido = this.asientoElegido.filter(i => i !== index);
    }
  }

  totalCompra(): number {
    return this.asientoElegido.length * this.precioBoleto;
  }

  comprarBoletos() {
    if (!this.salaElegida || !this.peliculaElegida || this.asientoElegido.length === 0) {
      this.mensajeError('Debes seleccionar sala, película y al menos un asiento.');
      return;
    }

    const usuario = this.session.obtenerUser();
    if (!usuario) {
      this.mensajeError('No hay usuario en sesión');
      return;
    }

    const boletos: Boleto[] = this.asientoElegido.map(index => {
      const fila = Math.floor(index / this.salaElegida!.columnaSala) + 1;
      const columna = (index % this.salaElegida!.columnaSala) + 1;
      return {
        idUsuario: usuario.idUsuario,
        idPeliculaSala: this.peliculaElegida!,
        precioBoleto: this.precioBoleto,
        filaAsiento: fila,
        columnaAsiento: columna,
        fechaCompra: new Date().toISOString().split('T')[0]
      };
    });

    boletos.forEach(boleto => {
      this.boletoService.comprarBoleto(boleto).subscribe({
        next: () => {
          this.seleccionarPelicula(); 
          this.mensajeExito('Compra realizada correctamente.');
        },
        error: err => this.mensajeError('Error al comprar boletos: ' + err.message)
      });
    });
  }

  mensajeExito(msg: string) {
    this.mensaje = msg;
    this.tipoMensaje = 'success';
  }

  mensajeError(msg: string) {
    this.mensaje = msg;
    this.tipoMensaje = 'danger';
  }

}
