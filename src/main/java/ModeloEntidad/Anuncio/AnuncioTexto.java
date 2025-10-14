/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Anuncio;

import EnumOptions.TipoAnuncio;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class AnuncioTexto extends Anuncio{

    public AnuncioTexto(String mensajeAnuncio, int idUsuario, LocalDate fechaInicio, LocalDate fechaFin, Integer diasVigente) {
        super(TipoAnuncio.TEXTO, mensajeAnuncio, idUsuario, fechaInicio, fechaFin, diasVigente);
    }
    
    
}
