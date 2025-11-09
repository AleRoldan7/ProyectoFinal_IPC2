/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Anuncio;

import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class BloqueoAnuncio {

    private Integer idBloqueo;
    private Integer idCine;
    private Integer diasBloqueo;
    private double costoTotal;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaPago;
    private boolean activo;

    public BloqueoAnuncio() {
    }

    public BloqueoAnuncio(Integer idCine, Integer diasBloqueo, double costoTotal,
            LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaPago) {
        this.idCine = idCine;
        this.diasBloqueo = diasBloqueo;
        this.costoTotal = costoTotal;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaPago = fechaPago;
        this.activo = true;
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

    public boolean esValido() {
        return idCine != null && idCine > 0
                && diasBloqueo != null && diasBloqueo > 0
                && costoTotal > 0
                && fechaInicio != null
                && fechaFin != null
                && fechaPago != null
                && fechaFin.isAfter(fechaInicio);
    }
}
