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
import { CrearPeliculaComponent } from '../pages/admin-sistema/crear-pelicula/crear-pelicula.component';
import { ListasComponent } from '../components/lista/listas.component';
import { AgregarPeliculaSalaComponent } from '../pages/admin-cine/agregar-pelicula-sala/agregar-pelicula-sala.component';
import { CrearAnuncioComponent } from '../components/anuncios/crear-anuncio.component';
import { AnunciosUsuarioComponent } from '../components/anuncios/anuncios-usuario/anuncios-usuario.component';
import { ComprarBoletosComponent } from '../components/boletos/comprar-boletos.component';
import { ReporteBoletoComponent } from '../components/reportes/reporte-boleto/reporte-boleto.component';
import { ComentariosComponent } from '../components/comentarios/comentarios.component';
import { PeliculasUsuarioComponent } from '../components/peliculas-usuario/peliculas-usuario.component';
import { ReporteSalaComentarioComponent } from '../components/reportes/reporte-comentarios-sala/reporte-sala-comentario.component';
import { SalasUsuarioComponent } from '../components/peliculas-usuario/salas-usuario/salas-usuario.component';
import { ReportePeliculaProyectadaComponent } from '../components/reportes/reporte-pelicula/reporte-pelicula-proyectada.component';
import { ReporteSalaMasGustadaComponent } from '../components/reportes/reporte-mas-gustadas/reporte-sala-mas-gustada.component';
import { ReporteSistemaComponent } from '../components/reportes/reportes-sistema/reporte-sistema.component';
import { BloquearComponent } from '../pages/admin-cine/bloquear/bloquear.component';
import { ActualizarSalaComponent } from '../pages/admin-cine/actualizar-salas/actualizar-sala.component';

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
        canActivate: [authGuard],
        data: { roles: ['USUARIO_COMUN'] },
    },
    {
        path: 'registro',
        component: Registrer,
    },
    {
        path: 'anunciante',
        component: AnuncianteComponent,
        canActivate: [authGuard],
        data: { roles: ['ANUNCIANTE'] },
    },
    {
        path: 'admin-cine',
        component: AdminCineComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_CINE'] },
    },
    {
        path: 'admin-sistema',
        component: AdminSistemaComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_SISTEMA'] },
    },
    {
        path: 'crear-cine',
        component: CrearCineComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_SISTEMA'] },
    },
    {
        path: 'cartera',
        component: CarteraComponent,
        canActivate: [authGuard],
        data: {
            roles: ['USUARIO_COMUN', 'ADMIN_CINE', 'ANUNCIANTE'],
        },
    },
    {
        path: 'crear-sala',
        component: CrearSalaComponent,
        canActivate: [authGuard],
        data: {
            roles: ['ADMIN_CINE'],
        },
    },
    {
        path: 'crear-pelicula',
        component: CrearPeliculaComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_SISTEMA'] },
    },
    {
        path: 'agregar-pelicula-sala',
        component: AgregarPeliculaSalaComponent,
        canActivate: [authGuard],
        data: {
            roles: ['ADMIN_CINE'],
        },
    },
    {
        path: 'crear-anuncio',
        component: CrearAnuncioComponent,
        canActivate: [authGuard],
        data: { roles: ['ANUNCIANTE'] },
    },
    {
        path: 'lista',
        component: ListasComponent,
    },
    {
        path: 'anuncios-usuario',
        component: AnunciosUsuarioComponent,
        canActivate: [authGuard],
        data: { roles: ['ANUNCIANTE'] },
    },
    {
        path: 'comprar-boleto',
        component: ComprarBoletosComponent,
        canActivate: [authGuard],
        data: { roles: ['USUARIO_COMUN'] },
    },
    {
        path: 'reporte/boleto',
        component: ReporteBoletoComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_CINE'] },
    },
    {
        path: 'reporte/sala-comentario',
        component: ReporteSalaComentarioComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_CINE'] },
    },
    {
        path: 'comentario',
        component: PeliculasUsuarioComponent,
        canActivate: [authGuard],
        data: { roles: ['USUARIO_COMUN', 'ADMIN_CINE', 'ADMIN_SISTEMA'] }
    },
    {
        path: 'comentario/sala',
        component: SalasUsuarioComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_CINE'] },
    },
    {
        path: 'reporte/pelicula-proyectada',
        component: ReportePeliculaProyectadaComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_CINE'] },
    },
    {
        path: 'reporte/salas-gustadas',
        component: ReporteSalaMasGustadaComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_CINE'] },
    },
    {
        path: 'reporte/sistema',
        component: ReporteSistemaComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_SISTEMA'] },
    },
    {
        path: 'bloquear',
        component: BloquearComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_CINE'] },
    },
    {
        path: 'actualizar-sala',
        component: ActualizarSalaComponent,
        canActivate: [authGuard],
        data: { roles: ['ADMIN_CINE'] },
    },
    {
        path: '**',
        redirectTo: '/login',
    },
];
