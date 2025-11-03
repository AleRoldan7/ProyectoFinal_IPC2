import { tipoAnuncio } from "./tipo.anuncio";

export interface Anuncio {
    idAnuncio: number;            
    tipoAnuncio: tipoAnuncio;
    mensajeAnuncio: string;
    nombreAnunciante: number;
    fechaInicio: string;
    diasVigente: number;
    imagenAnuncio?: File | null;
    videoAnuncio?: string | null;
}