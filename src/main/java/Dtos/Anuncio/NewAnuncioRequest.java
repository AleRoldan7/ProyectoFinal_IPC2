/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Anuncio;

import EnumOptions.TipoAnuncio;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.io.InputStream;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class NewAnuncioRequest {

    private TipoAnuncio tipoAnuncio;
    private String mensajeAnuncio;
    private InputStream imagenAnuncio;
    private String videoAnuncio;
    private int nombreAnunciante;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaInicio;
    private int diasVigencia;
    private double precio;

    public TipoAnuncio getTipoAnuncio() {
        return tipoAnuncio;
    }

    public void setTipoAnuncio(TipoAnuncio tipoAnuncio) {
        this.tipoAnuncio = tipoAnuncio;
    }

    public String getMensajeAnuncio() {
        return mensajeAnuncio;
    }

    public void setMensajeAnuncio(String mensajeAnuncio) {
        this.mensajeAnuncio = mensajeAnuncio;
    }

    public InputStream getImagenAnuncio() {
        return imagenAnuncio;
    }

    public void setImagenAnuncio(InputStream imagenAnuncio) {
        this.imagenAnuncio = imagenAnuncio;
    }

    public String getVideoAnuncio() {
        return videoAnuncio;
    }

    public void setVideoAnuncio(String videoAnuncio) {
        this.videoAnuncio = videoAnuncio;
    }

    public int getNombreAnunciante() {
        return nombreAnunciante;
    }

    public void setNombreAnunciante(int nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getDiasVigencia() {
        return diasVigencia;
    }

    public void setDiasVigencia(int diasVigencia) {
        this.diasVigencia = diasVigencia;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
