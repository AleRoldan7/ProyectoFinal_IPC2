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
public class AnuncioImagen extends Anuncio{
    
    private byte [] imagenAnuncio;

    public AnuncioImagen(byte[] imagenAnuncio, String mensajeAnuncio, int idUsuario, LocalDate fechaInicio, 
            LocalDate fechaFin, Integer diasVigente) {
        super(TipoAnuncio.TEXTO_IMAGEN, mensajeAnuncio, idUsuario, fechaInicio, fechaFin, diasVigente);
        this.imagenAnuncio = imagenAnuncio;
    }

    public byte[] getImagenAnuncio() {
        return imagenAnuncio;
    }

    public void setImagenAnuncio(byte[] imagenAnuncio) {
        this.imagenAnuncio = imagenAnuncio;
    }
    
    
}
