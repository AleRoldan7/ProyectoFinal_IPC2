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
public class ReporteSalaComentadaResponse {

    private String nombreSala;
    private String nombreCine;
    private Integer totalComentarios;
    private List<ComentarioDetalle> comentarios;

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

    public Integer getTotalComentarios() {
        return totalComentarios;
    }

    public void setTotalComentarios(Integer totalComentarios) {
        this.totalComentarios = totalComentarios;
    }

    public List<ComentarioDetalle> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDetalle> comentarios) {
        this.comentarios = comentarios;
    }

    public static class ComentarioDetalle {

        private String usuario;
        private String comentario;
        private Integer calificacion;
        private String fecha;

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public Integer getCalificacion() {
            return calificacion;
        }

        public void setCalificacion(Integer calificacion) {
            this.calificacion = calificacion;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
    }
}
