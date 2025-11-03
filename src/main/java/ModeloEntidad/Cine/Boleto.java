/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Cine;

import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class Boleto {

    private Integer idBoleto;
    private int idUsuario;
    private int idPeliculaSala;
    private double precioBoleto;
    private int filaAsiento;
    private int columnaAsiento;
    private LocalDate fechaCompra;

    public Boleto() {
    }

    public Boleto(Integer idBoleto, int idUsuario, int idPeliculaSala, double precioBoleto, int filaAsiento, int columnaAsiento,
            LocalDate fechaCompra) {
        this.idBoleto = idBoleto;
        this.idUsuario = idUsuario;
        this.idPeliculaSala = idPeliculaSala;
        this.precioBoleto = precioBoleto;
        this.filaAsiento = filaAsiento;
        this.columnaAsiento = columnaAsiento;
        this.fechaCompra = fechaCompra;
    }

    public Boleto(int idUsuario, int idPeliculaSala, double precioBoleto, int filaAsiento, int columnaAsiento, LocalDate fechaCompra) {
        this.idUsuario = idUsuario;
        this.idPeliculaSala = idPeliculaSala;
        this.precioBoleto = precioBoleto;
        this.filaAsiento = filaAsiento;
        this.columnaAsiento = columnaAsiento;
        this.fechaCompra = fechaCompra;
    }

    
    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
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
