import { Routes } from '@angular/router';
import { Login } from '../pages/login/login.component';
import { Registrer } from '../pages/registrer/registrer.component';
import { UsuarioComunComponent } from '../pages/usuario-comun/usuario-comun.component';
import { AnuncianteComponent } from '../pages/anunciante/anunciante.component';
import { AdminCineComponent } from '../pages/admin-cine/admin-cine.component';
import { AdminSistemaComponent } from '../pages/admin-sistema/admin-sistema.component';
import { CarteraComponent } from '../pages/cartera/cartera.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full',
    },
    {
        path: 'login',
        component: Login,
    },
    {
        path: 'usuario-comun',
        component: UsuarioComunComponent,
    },
    {
        path: 'registro',
        component: Registrer,
    },
    {
        path: 'anunciante',
        component: AnuncianteComponent,
    },
    {
        path: 'admin-cine',
        component: AdminCineComponent,
    },
    {
        path: 'admin-sistema',
        component: AdminSistemaComponent,
    },
    {
        path: 'cartera',
        component: CarteraComponent,
    },
    {
        path: '**',
        redirectTo: '/login',
    }


];