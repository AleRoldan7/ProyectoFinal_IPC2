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
public class Cine {
    
    private int idCine;
    private String nombreCine;
    private int idUsuario;
    private LocalDate fechaCreacion;
    private double costoCine;

    public Cine(String nombreCine, LocalDate fechaCreacion) {
        this.nombreCine = nombreCine;
        this.fechaCreacion = fechaCreacion;
    }

    
    
    public Cine() {
        
    }

    public Cine(int idCine, String nombreCine, int idUsuario, LocalDate fechaCreacion, double costoCine) {
        this.idCine = idCine;
        this.nombreCine = nombreCine;
        this.idUsuario = idUsuario;
        this.fechaCreacion = fechaCreacion;
        this.costoCine = costoCine;
    }

    public int getIdCine() {
        return idCine;
    }

    public void setIdCine(int idCine) {
        this.idCine = idCine;
    }

    public String getNombreCine() {
        return nombreCine;
    }

    public void setNombreCine(String nombreCine) {
        this.nombreCine = nombreCine;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getCostoCine() {
        return costoCine;
    }

    public void setCostoCine(double costoCine) {
        this.costoCine = costoCine;
    }
    
    
}
