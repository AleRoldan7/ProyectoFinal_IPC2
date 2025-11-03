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
public class Anuncio {

    protected Integer idAnuncio;
    protected TipoAnuncio tipoAnuncio;
    protected String mensajeAnuncio;
    protected int idUsuario;
    protected LocalDate fechaInicio;
    protected Integer diasVigente;
    protected boolean anuncioVencido = false;
    protected boolean anuncioActivo = true;
    protected double precio;

    public Anuncio() {
    }

    public Anuncio(Integer idAnuncio, TipoAnuncio tipoAnuncio, String mensajeAnuncio, int idUsuario,
            LocalDate fechaInicio, Integer diasVigente, double precio) {
        this.idAnuncio = idAnuncio;
        this.tipoAnuncio = tipoAnuncio;
        this.mensajeAnuncio = mensajeAnuncio;
        this.idUsuario = idUsuario;
        this.fechaInicio = fechaInicio;
        this.diasVigente = diasVigente;
        this.precio = precio;
    }

    public Integer getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(Integer idAnuncio) {
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getDiasVigente() {
        return diasVigente;
    }

    public void setDiasVigente(Integer diasVigente) {
        this.diasVigente = diasVigente;
    }

    public boolean isAnuncioVencido() {
        return anuncioVencido;
    }

    public void setAnuncioVencido(boolean anuncioVencido) {
        this.anuncioVencido = anuncioVencido;
    }

    public boolean isAnuncioActivo() {
        return anuncioActivo;
    }

    public void setAnuncioActivo(boolean anuncioActivo) {
        this.anuncioActivo = anuncioActivo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
