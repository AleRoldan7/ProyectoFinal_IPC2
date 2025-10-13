import { TipoUsuario } from "./tipo.usuario";

export interface Usuario {

    nombre: string;
    userName: string;
    password: string;
    rolUsuario: TipoUsuario;
    fechaRegistro: Date;
}