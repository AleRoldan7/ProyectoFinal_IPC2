/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Boleto;

import ModeloEntidad.Cine.Boleto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class BoletoResponse {

    private int idUsuario;
    private int idPeliculaSala;
    private double precioBoleto;
    private int filaAsiento;
    private int columnaAsiento;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaCompra;

    public BoletoResponse(Boleto boleto) {
        this.idUsuario = boleto.getIdUsuario();
        this.idPeliculaSala = boleto.getIdProyeccion();
        this.precioBoleto = boleto.getPrecioBoleto();
        this.filaAsiento = boleto.getFilaAsiento();
        this.columnaAsiento = boleto.getColumnaAsiento();
        this.fechaCompra = boleto.getFechaCompra();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPeliculaSala() {
        return idPeliculaSala;
    }

    public void setIdPeliculaSala(int idPeliculaSala) {
        this.idPeliculaSala = idPeliculaSala;
    }

    public double getPrecioBoleto() {
        return precioBoleto;
    }

    public void setPrecioBoleto(double precioBoleto) {
        this.precioBoleto = precioBoleto;
    }

    public int getFilaAsiento() {
        return filaAsiento;
    }

    public void setFilaAsiento(int filaAsiento) {
        this.filaAsiento = filaAsiento;
    }

    public int getColumnaAsiento() {
        return columnaAsiento;
    }

    public void setColumnaAsiento(int columnaAsiento) {
        this.columnaAsiento = columnaAsiento;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    
    

}
