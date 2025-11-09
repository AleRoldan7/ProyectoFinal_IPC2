/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Anuncio.Bloqueo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class BloqueoAnuncioResponse {

    private Integer idBloqueo;
    private Integer idCine;
    private Integer diasBloqueo;
    private double costoTotal;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaInicio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaFin;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaPago;
    private boolean activo;
    private String mensaje;

    public BloqueoAnuncioResponse() {
    }

    public BloqueoAnuncioResponse(Integer idBloqueo, Integer idCine, Integer diasBloqueo,
            double costoTotal, LocalDate fechaInicio, LocalDate fechaFin,
            LocalDate fechaPago, boolean activo, String mensaje) {
        this.idBloqueo = idBloqueo;
        this.idCine = idCine;
        this.diasBloqueo = diasBloqueo;
        this.costoTotal = costoTotal;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaPago = fechaPago;
        this.activo = activo;
        this.mensaje = mensaje;
    }

    public Integer getIdBloqueo() {
        return idBloqueo;
    }

    public void setIdBloqueo(Integer idBloqueo) {
        this.idBloqueo = idBloqueo;
    }

    public Integer getIdCine() {
        return idCine;
    }

    public void setIdCine(Integer idCine) {
        this.idCine = idCine;
    }

    public Integer getDiasBloqueo() {
        return diasBloqueo;
    }

    public void setDiasBloqueo(Integer diasBloqueo) {
        this.diasBloqueo = diasBloqueo;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
