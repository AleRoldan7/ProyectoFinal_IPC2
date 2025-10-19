import { Routes } from '@angular/router';
import { Login } from '../pages/login/login.component';
import { Registrer } from '../pages/registrer/registrer.component';
import { UsuarioComunComponent } from '../pages/usuario-comun/usuario-comun.component';
import { AnuncianteComponent } from '../pages/anunciante/anunciante.component';
import { AdminCineComponent } from '../pages/admin-cine/admin-cine.component';
import { AdminSistemaComponent } from '../pages/admin-sistema/admin-sistema.component';
import { CarteraComponent } from '../pages/cartera/cartera.component';
import { CrearCineComponent } from '../pages/admin-sistema/crear-cine/crear-cine.component';
import { authGuard } from '../guards/auth-guard';
import { CrearSalaComponent } from '../pages/admin-cine/crear-sala/crear-sala.component';

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
        path: 'crear-cine',
        component: CrearCineComponent,
        canActivate: [authGuard],
        data: {
            roles: ['ADMIN_SISTEMA'],
        }
    },
    {
        path: 'cartera',
        component: CarteraComponent,
        canActivate: [authGuard],
        data: {
            roles: ['USUARIO_COMUN', 'ADMIN_CINE', 'ANUNCIANTE']
        }
    },
    {
        path: 'crear-sala',
        component: CrearSalaComponent,
        canActivate: [authGuard],
        data: {
            roles: ['ADMIN_CINE'],
        }
    },
    {
        path: '**',
        redirectTo: '/login',
    },
];
