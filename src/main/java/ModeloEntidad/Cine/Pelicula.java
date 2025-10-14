/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Cine;

import java.time.LocalTime;

/**
 *
 * @author alejandro
 */
public class Pelicula {
    
    private Integer idPelicula;
    private String tituloPelicula;
    private String sinopsinPelicula;
    private LocalTime duracionPelicula;
    private String castPelicula;
    private String directorPelicula;
    private byte [] posterPelicula;

    public Pelicula(String tituloPelicula, String sinopsinPelicula, LocalTime duracionPelicula, String castPelicula, 
            String directorPelicula, byte[] posterPelicula) {
        this.tituloPelicula = tituloPelicula;
        this.sinopsinPelicula = sinopsinPelicula;
        this.duracionPelicula = duracionPelicula;
        this.castPelicula = castPelicula;
        this.directorPelicula = directorPelicula;
        this.posterPelicula = posterPelicula;
    }

    
    public Pelicula() {
        
    }
    
    public Pelicula(Integer idPelicula, String tituloPelicula, String sinopsinPelicula, LocalTime duracionPelicula, 
            String castPelicula, String directorPelicula, byte[] posterPelicula) {
        this.idPelicula = idPelicula;
        this.tituloPelicula = tituloPelicula;
        this.sinopsinPelicula = sinopsinPelicula;
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

    public String getSinopsinPelicula() {
        return sinopsinPelicula;
    }

    public void setSinopsinPelicula(String sinopsinPelicula) {
        this.sinopsinPelicula = sinopsinPelicula;
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
    
    
}
