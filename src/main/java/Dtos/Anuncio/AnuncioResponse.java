/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Anuncio;

import EnumOptions.TipoAnuncio;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class AnuncioResponse {

    private int idAnuncio;
    private TipoAnuncio tipoAnuncio;
    private String mensajeAnuncio;
    private String videoAnuncio;
    private int nombreAnunciante;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaInicio;
    private int diasVigencia;
    private double precio;
    public boolean anuncioActivo;
    public boolean anuncioVencido;

    public AnuncioResponse(int idAnuncio, TipoAnuncio tipoAnuncio, String mensajeAnuncio,
            String videoAnuncio, int nombreAnunciante, LocalDate fechaInicio,
            int diasVigencia, double precio) {
        this.idAnuncio = idAnuncio;
        this.tipoAnuncio = tipoAnuncio;
        this.mensajeAnuncio = mensajeAnuncio;
        this.videoAnuncio = videoAnuncio;
        this.nombreAnunciante = nombreAnunciante;
        this.fechaInicio = fechaInicio;
        this.diasVigencia = diasVigencia;
        this.precio = precio;
    }

    public AnuncioResponse() {
    }

    public int getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(int idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

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

    public boolean isAnuncioActivo() {
        return anuncioActivo;
    }

    public void setAnuncioActivo(boolean anuncioActivo) {
        this.anuncioActivo = anuncioActivo;
    }

    public boolean isAnuncioVencido() {
        return anuncioVencido;
    }

    public void setAnuncioVencido(boolean anuncioVencido) {
        this.anuncioVencido = anuncioVencido;
    }

}
