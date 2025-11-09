/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Reportes.Sistema;

/**
 *
 * @author alejandro
 */
public class ReporteGananciaResponse {
    
    private String nombreCine;
    private double costoCine;
    private double ingresosAnuncios;
    private double ingresosBloqueos;
    private double totalCostos;
    private double totalIngresos;
    private double ganancia;

    public String getNombreCine() {
        return nombreCine;
    }

    public void setNombreCine(String nombreCine) {
        this.nombreCine = nombreCine;
    }

    public double getCostoCine() {
        return costoCine;
    }

    public void setCostoCine(double costoCine) {
        this.costoCine = costoCine;
    }

    public double getIngresosAnuncios() {
        return ingresosAnuncios;
    }

    public void setIngresosAnuncios(double ingresosAnuncios) {
        this.ingresosAnuncios = ingresosAnuncios;
    }

    public double getIngresosBloqueos() {
        return ingresosBloqueos;
    }

    public void setIngresosBloqueos(double ingresosBloqueos) {
        this.ingresosBloqueos = ingresosBloqueos;
    }

    public double getTotalCostos() {
        return totalCostos;
    }

    public void setTotalCostos(double totalCostos) {
        this.totalCostos = totalCostos;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }

}
