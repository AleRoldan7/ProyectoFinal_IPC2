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
    
    private Integer idAnuncio;
    private TipoAnuncio tipoAnuncio;
    private String mensajeAnuncio;
    private int idUsuario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer diasVigente;
    private boolean anuncioVencido = false;
    private boolean anuncioActivo = false;

    public Anuncio(Integer idAnuncio, TipoAnuncio tipoAnuncio, String mensajeAnuncio, int idUsuario, LocalDate fechaInicio, 
            LocalDate fechaFin, Integer diasVigente) {
        this.idAnuncio = idAnuncio;
        this.tipoAnuncio = tipoAnuncio;
        this.mensajeAnuncio = mensajeAnuncio;
        this.idUsuario = idUsuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasVigente = diasVigente;
    }

    public Anuncio() {
    }

    public Anuncio(TipoAnuncio tipoAnuncio, String mensajeAnuncio, int idUsuario, LocalDate fechaInicio, 
            LocalDate fechaFin, Integer diasVigente) {
        this.tipoAnuncio = tipoAnuncio;
        this.mensajeAnuncio = mensajeAnuncio;
        this.idUsuario = idUsuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasVigente = diasVigente;
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

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
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
    
    
}
