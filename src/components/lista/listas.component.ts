import { Component, OnInit } from '@angular/core';
import { Lista } from '../../services/lista/lista';

@Component({
  selector: 'app-listas',
  imports: [],
  templateUrl: './listas.component.html',
  styleUrl: './listas.component.css'
})
export class ListasComponent implements OnInit {

  usuarios: any[] = [];
  rolUsuario = 'ADMIN_CINE';

  constructor(private listaService: Lista) {}

  ngOnInit(): void {
      this.listaAdminCine();
  }

  listaAdminCine() {
    this.listaService.obtenerAdminCine(this.rolUsuario).subscribe({
      next: (admin) => {
        this.usuarios = Array.isArray(admin) ? admin: [admin];
      },
      error: (e) => {
        console.log('No jaloooo', e);
      },
    });
  }
}
