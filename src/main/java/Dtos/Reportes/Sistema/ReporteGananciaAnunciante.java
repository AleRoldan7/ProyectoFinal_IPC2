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
public class ReporteGananciaAnunciante {

    private String nombreAnunciante;
    private double totalGastado;
    private Integer totalAnuncios;
    private List<AnuncioDetalle> anuncios;

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public Integer getTotalAnuncios() {
        return totalAnuncios;
    }

    public void setTotalAnuncios(Integer totalAnuncios) {
        this.totalAnuncios = totalAnuncios;
    }

    public List<AnuncioDetalle> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(List<AnuncioDetalle> anuncios) {
        this.anuncios = anuncios;
    }

    public static class AnuncioDetalle {

        private Integer idAnuncio;
        private String tipoAnuncio;
        private double costo;
        private String periodo;

        public Integer getIdAnuncio() {
            return idAnuncio;
        }

        public void setIdAnuncio(Integer idAnuncio) {
            this.idAnuncio = idAnuncio;
        }

        public String getTipoAnuncio() {
            return tipoAnuncio;
        }

        public void setTipoAnuncio(String tipoAnuncio) {
            this.tipoAnuncio = tipoAnuncio;
        }

        public double getCosto() {
            return costo;
        }

        public void setCosto(double costo) {
            this.costo = costo;
        }

        public String getPeriodo() {
            return periodo;
        }

        public void setPeriodo(String periodo) {
            this.periodo = periodo;
        }
    }
}
