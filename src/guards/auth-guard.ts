import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  const usuario = localStorage.getItem('usuario');

  if (!usuario) {
    alert('Tienes que iniciar sesion');
    router.navigate(["/login"]);
    return false;
  }

  const userData = JSON.parse(usuario);
  const rolUsuario = userData.rolUsuario;

  const expectedRoles: string[] = route.data['roles'];

  if (expectedRoles && !expectedRoles.includes(rolUsuario)) {
    alert('No tienes permiso para acceder a esta ruta');
    router.navigate(['/']);
    return false;
  }

  return true;
};
