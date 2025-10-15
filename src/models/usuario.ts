import { Rol } from "./tipo.usuario";

export interface Usuario {

    nombre: string;
    userName: string;
    password: string;
    rolUsuario: Rol;
    fechaRegistro: Date;
}