/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Cine;


/**
 *
 * @author alejandro
 */
public class Sala {
    
    private String nombreSala;
    private int filaSala;
    private int columnaSala;

    public Sala(String nombreSala, int filaSala, int columnaSala) {
        this.nombreSala = nombreSala;
        this.filaSala = filaSala;
        this.columnaSala = columnaSala;
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

   
   
    
    
}
