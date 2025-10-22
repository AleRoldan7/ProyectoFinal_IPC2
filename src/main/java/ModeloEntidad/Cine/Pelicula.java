/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Cine;

import java.time.LocalTime;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alejandro
 */
public class Pelicula {

    private Integer idPelicula;
    private String tituloPelicula;
    private String sinopsisPelicula;
    private LocalTime duracionPelicula;
    private String castPelicula;
    private String directorPelicula;
    private byte[] posterPelicula;

    public Pelicula(String tituloPelicula, String sinopsisPelicula, LocalTime duracionPelicula, String castPelicula,
            String directorPelicula, byte[] posterPelicula) {
        this.tituloPelicula = tituloPelicula;
        this.sinopsisPelicula = sinopsisPelicula;
        this.duracionPelicula = duracionPelicula;
        this.castPelicula = castPelicula;
        this.directorPelicula = directorPelicula;
        this.posterPelicula = posterPelicula;
    }

    public Pelicula() {

    }

    public Pelicula(Integer idPelicula, String tituloPelicula, String sinopsisPelicula, LocalTime duracionPelicula,
            String castPelicula, String directorPelicula, byte[] posterPelicula) {
        this.idPelicula = idPelicula;
        this.tituloPelicula = tituloPelicula;
        this.sinopsisPelicula = sinopsisPelicula;
        this.duracionPelicula = duracionPelicula;
        this.castPelicula = castPelicula;
        this.directorPelicula = directorPelicula;
        this.posterPelicula = posterPelicula;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTituloPelicula() {
        return tituloPelicula;
    }

    public void setTituloPelicula(String tituloPelicula) {
        this.tituloPelicula = tituloPelicula;
    }

    public String getSinopsisPelicula() {
        return sinopsisPelicula;
    }

    public void setSinopsisPelicula(String sinopsinPelicula) {
        this.sinopsisPelicula = sinopsinPelicula;
    }

    public LocalTime getDuracionPelicula() {
        return duracionPelicula;
    }

    public void setDuracionPelicula(LocalTime duracionPelicula) {
        this.duracionPelicula = duracionPelicula;
    }

    public String getCastPelicula() {
        return castPelicula;
    }

    public void setCastPelicula(String castPelicula) {
        this.castPelicula = castPelicula;
    }

    public String getDirectorPelicula() {
        return directorPelicula;
    }

    public void setDirectorPelicula(String directorPelicula) {
        this.directorPelicula = directorPelicula;
    }

    public byte[] getPosterPelicula() {
        return posterPelicula;
    }

    public void setPosterPelicula(byte[] posterPelicula) {
        this.posterPelicula = posterPelicula;
    }

    public boolean esValido() {
        return StringUtils.isNotBlank(tituloPelicula)
                && StringUtils.isNotBlank(sinopsisPelicula)
                && duracionPelicula != null
                && StringUtils.isNotBlank(castPelicula)
                && StringUtils.isNotBlank(directorPelicula);
    }
}
