/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Cine;

/**
 *
 * @author alejandro
 */
public class SalaCine {
    
    private Integer idSalaCine;
    private int idSala;
    private int idCine;

    public SalaCine() {
    }

    
    public SalaCine(Integer idSalaCine, int idSala, int idCine) {
        this.idSalaCine = idSalaCine;
        this.idSala = idSala;
        this.idCine = idCine;
    }

    
    public Integer getIdSalaCine() {
        return idSalaCine;
    }

    public void setIdSalaCine(Integer idSalaCine) {
        this.idSalaCine = idSalaCine;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getIdCine() {
        return idCine;
    }

    public void setIdCine(int idCine) {
        this.idCine = idCine;
    }
    
    
}
