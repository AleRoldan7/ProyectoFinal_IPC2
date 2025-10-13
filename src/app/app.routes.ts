import { Routes } from '@angular/router';
import { Login } from '../pages/login/login.component';
import { UsuarioComponent } from '../pages/usuario/usuario.component';
import { Registrer } from '../pages/registrer/registrer.component';

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
        component: UsuarioComponent,
    },
    {
        path: 'registro',
        component: Registrer,
    },
    {
        path: '**',
        redirectTo: '/login',
    }


];
