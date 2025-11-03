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
public class AnuncioImagen extends Anuncio {

    private byte[] imagenAnuncio;

    public AnuncioImagen(Integer idAnuncio, String mensajeAnuncio, byte[] imagenAnuncio, int idUsuario,
            LocalDate fechaInicio, Integer diasVigente, double precio) {
        super(idAnuncio, TipoAnuncio.TEXTO_IMAGEN, mensajeAnuncio, idUsuario, fechaInicio, diasVigente, precio);
        this.imagenAnuncio = imagenAnuncio;
    }

    public AnuncioImagen() {
        this.tipoAnuncio = TipoAnuncio.TEXTO_IMAGEN;
    }

    public byte[] getImagenAnuncio() {
        return imagenAnuncio;
    }

    public void setImagenAnuncio(byte[] imagenAnuncio) {
        this.imagenAnuncio = imagenAnuncio;
    }

}
