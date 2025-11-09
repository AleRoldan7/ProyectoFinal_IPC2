export interface ReporteBoleto {
    idSala: number;
    nombreSala: string;
    idUsuario: number;
    nombreUsuario: string;
    tituloPelicula: string;
    precioBoleto: number;
    filaAsiento: number;
    columnaAsiento: number;
    totalDinero: number;
}