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
public class AnuncioVideo extends Anuncio {

    private String videoAnuncio;

    public AnuncioVideo(Integer idAnuncio, String mensajeAnuncio, String videoAnuncio, int idUsuario,
            LocalDate fechaInicio, Integer diasVigente, double precio) {
        super(idAnuncio, TipoAnuncio.TEXTO_VIDEO, mensajeAnuncio, idUsuario, fechaInicio, diasVigente, precio);
        this.videoAnuncio = videoAnuncio;
    }

    public AnuncioVideo() {
        this.tipoAnuncio = TipoAnuncio.TEXTO_VIDEO;
    }

    public String getVideoAnuncio() {
        return videoAnuncio;
    }

    public void setVideoAnuncio(String videoAnuncio) {
        this.videoAnuncio = videoAnuncio;
    }

}
