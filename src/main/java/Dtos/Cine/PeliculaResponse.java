/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Cine;

import ModeloEntidad.Cine.Pelicula;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.time.LocalTime;

/**
 *
 * @author alejandro
 */
public class PeliculaResponse {
    
    private String tituloPelicula;
    private String sinopsisPelicula;
    @JsonFormat(pattern = "HH:mm")
    @JsonSerialize (using = LocalTimeSerializer.class)
    private LocalTime duracionPelicula;
    private String castPelicula;
    private String directorPelicula;
    private byte [] posterPelicula;

    public PeliculaResponse(Pelicula pelicula) {
        this.tituloPelicula = tituloPelicula;
        this.sinopsisPelicula = sinopsisPelicula;
        this.duracionPelicula = duracionPelicula;
        this.castPelicula = castPelicula;
        this.directorPelicula = directorPelicula;
        this.posterPelicula = posterPelicula;
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

    public void setSinopsisPelicula(String sinopsisPelicula) {
        this.sinopsisPelicula = sinopsisPelicula;
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
