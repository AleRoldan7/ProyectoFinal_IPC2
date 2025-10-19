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
public class Sala {

    private Integer idSala;
    private int idUsuario;
    private String nombreSala;
    private int filaSala;
    private int columnaSala;
    private LocalDate fechaCreacion;

    public Sala() {
    }

    public Sala(Integer idSala, int idUsuario, String nombreSala, int filaSala, int columnaSala, LocalDate fechaCreacion) {
        this.idSala = idSala;
        this.idUsuario = idUsuario;
        this.nombreSala = nombreSala;
        this.filaSala = filaSala;
        this.columnaSala = columnaSala;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public int getFilaSala() {
        return filaSala;
    }

    public void setFilaSala(int filaSala) {
        this.filaSala = filaSala;
    }

    public int getColumnaSala() {
        return columnaSala;
    }

    public void setColumnaSala(int columnaSala) {
        this.columnaSala = columnaSala;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
