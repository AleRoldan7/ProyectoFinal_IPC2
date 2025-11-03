import { inject, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { SessionService } from '../services/session-service/session-service';

@Injectable({
  providedIn: 'root'
})

export class authGuard implements CanActivate {

  constructor(private router: Router, private sessionService: SessionService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const usuarioData = this.sessionService.obtenerUser();
    if (!usuarioData) {
      this.router.navigate(['/login']);
      return false;
    }


    const rol = usuarioData.rolUsuario;
    const allowedRoles = route.data['roles'] as string[];

    if (!allowedRoles.includes(rol)) {
      console.warn(`Acceso denegado: rol ${rol} no permitido`);
      this.router.navigate(['/login']);
      return false;
    }

    console.log(`Acceso permitido para el rol: ${rol}`);
    return true;
  }

}
