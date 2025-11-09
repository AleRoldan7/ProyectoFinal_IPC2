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

  cartelera: any[] = [];
  carteleraAgrupada: { cine: string; funciones: any[] }[] = [];
  busqueda: string = '';
  funcionSeleccionada: any;
  asientos: boolean[] = [];
  ocupados: boolean[] = [];
  seleccionados: number[] = [];
  mensaje: string = '';
  tipoMensaje: 'success' | 'danger' = 'success';

  constructor(private boletoService: BoletoService, private session: SessionService, private lista: Lista) { }


  ngOnInit(): void {
    this.cargarCarteleraGlobal();
  }

  cargarCarteleraGlobal() {
    this.boletoService.obtenerCarteleraGlobal(this.busqueda).subscribe({
      next: (data) => {
        this.cartelera = data;
        this.agruparPorCine(); 
        this.mostrarMensaje(
          data.length === 0
            ? 'No hay funciones hoy'
            : `${data.length} funciones encontradas`,
          data.length === 0 ? 'danger' : 'success'
        );
      },
      error: () => this.mostrarMensaje('Error al cargar cartelera', 'danger')
    });
  }

  agruparPorCine() {
    const grupos = new Map<string, any[]>();
    this.cartelera.forEach(f => {
      const cine = f.cine || 'Sin cine';
      if (!grupos.has(cine)) {
        grupos.set(cine, []);
      }
      grupos.get(cine)!.push(f);
    });

    this.carteleraAgrupada = Array.from(grupos.entries()).map(([cine, funciones]) => ({
      cine,
      funciones
    }));
  }

  buscar() {
    this.cargarCarteleraGlobal();
  }

  seleccionarFuncion(f: any) {
    this.funcionSeleccionada = f;
    this.inicializarAsientos(f.filas, f.columnas);
    this.cargarAsientosOcupados(f.idProyeccion);
  }

  inicializarAsientos(filas: number, columnas: number) {
    const total = filas * columnas;
    this.asientos = new Array(total).fill(false);
    this.ocupados = new Array(total).fill(false);
    this.seleccionados = [];
  }

  cargarAsientosOcupados(idProyeccion: number) {
    this.boletoService.obtenerAsientosOcupados(idProyeccion).subscribe({
      next: (data: any[]) => {
        data.forEach(a => {
          const i = (a.fila - 1) * this.funcionSeleccionada.columnas + (a.columna - 1);
          this.ocupados[i] = true;
        });
      }
    });
  }

  toggleAsiento(i: number) {
    if (this.ocupados[i]) return;
    this.asientos[i] = !this.asientos[i];
    if (this.asientos[i]) {
      this.seleccionados.push(i);
    } else {
      this.seleccionados = this.seleccionados.filter(x => x !== i);
    }
  }

  filaColumna(i: number): string {
    const fila = Math.floor(i / this.funcionSeleccionada.columnas) + 1;
    const col = (i % this.funcionSeleccionada.columnas) + 1;
    return `${fila}-${col}`;
  }

  total(): number {
    return this.seleccionados.length * this.funcionSeleccionada.precio;
  }

  comprar() {
    if (this.seleccionados.length === 0) return;

    const user = this.session.obtenerUser();
    if (!user) {
      this.mostrarMensaje('Inicia sesión para comprar', 'danger');
      return;
    }

    const fecha = new Date().toISOString().split('T')[0];

    const requests = this.seleccionados.map(i => {
      const fila = Math.floor(i / this.funcionSeleccionada.columnas) + 1;
      const col = (i % this.funcionSeleccionada.columnas) + 1;
      return {
        idUsuario: user.idUsuario,
        idPeliculaSala: this.funcionSeleccionada.idProyeccion,
        precioBoleto: this.funcionSeleccionada.precio,
        filaAsiento: fila,
        columnaAsiento: col,
        fechaCompra: fecha
      };
    });

    let completados = 0;
    requests.forEach(req => {
      this.boletoService.comprarBoleto(req).subscribe({
        next: () => {
          completados++;
          if (completados === requests.length) {
            this.mostrarMensaje(`¡${requests.length} boletos comprados! Total: Q${this.total()}`, 'success');
            this.cargarAsientosOcupados(this.funcionSeleccionada.idProyeccion);
            this.seleccionados = [];
            this.asientos.fill(false);
          }
        },
        error: (err) => {
          this.mostrarMensaje(err.error || 'Error en compra', 'danger');
        }
      });
    });
  }

  volverCartelera() {
    this.funcionSeleccionada = null;
    this.cargarCarteleraGlobal();
  }

  mostrarMensaje(msg: string, tipo: 'success' | 'danger') {
    this.mensaje = msg;
    this.tipoMensaje = tipo;
    setTimeout(() => this.mensaje = '', 6000);
  }

}
