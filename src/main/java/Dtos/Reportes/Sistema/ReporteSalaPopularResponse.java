/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Reportes.Sistema;

import java.util.List;

/**
 *
 * @author alejandro
 */
public class ReporteSalaPopularResponse {

    private String nombreSala;
    private String nombreCine;
    private Double promedioCalificacion;
    private Integer totalBoletos;
    private List<UsuarioBoleto> usuarios;

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public String getNombreCine() {
        return nombreCine;
    }

    public void setNombreCine(String nombreCine) {
        this.nombreCine = nombreCine;
    }

    public Double getPromedioCalificacion() {
        return promedioCalificacion;
    }

    public void setPromedioCalificacion(Double promedioCalificacion) {
        this.promedioCalificacion = promedioCalificacion;
    }

    public Integer getTotalBoletos() {
        return totalBoletos;
    }

    public void setTotalBoletos(Integer totalBoletos) {
        this.totalBoletos = totalBoletos;
    }

    public List<UsuarioBoleto> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioBoleto> usuarios) {
        this.usuarios = usuarios;
    }

    public static class UsuarioBoleto {

        private String nombreUsuario;
        private Integer boletosComprados;

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public Integer getBoletosComprados() {
            return boletosComprados;
        }

        public void setBoletosComprados(Integer boletosComprados) {
            this.boletosComprados = boletosComprados;
        }
    }
}
