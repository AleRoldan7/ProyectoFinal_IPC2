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
public class AnuncioTexto extends Anuncio {

    public AnuncioTexto(Integer idAnuncio, String mensajeAnuncio, int idUsuario,
            LocalDate fechaInicio, Integer diasVigente, double precio) {
        super(idAnuncio, TipoAnuncio.TEXTO, mensajeAnuncio, idUsuario, fechaInicio, diasVigente, precio);
    }

    public AnuncioTexto() {
        this.tipoAnuncio = TipoAnuncio.TEXTO;
    }

}
