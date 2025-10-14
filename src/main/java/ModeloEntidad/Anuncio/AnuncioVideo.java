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
public class AnuncioVideo extends Anuncio{
    
    private String linkVideo;

    public AnuncioVideo(String linkVideo, String mensajeAnuncio, int idUsuario, LocalDate fechaInicio, LocalDate fechaFin, Integer diasVigente) {
        super(TipoAnuncio.TEXTO_VIDEO, mensajeAnuncio, idUsuario, fechaInicio, fechaFin, diasVigente);
        this.linkVideo = linkVideo;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }
    
    
}
