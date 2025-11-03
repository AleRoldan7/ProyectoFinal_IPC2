/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EnumOptions;

/**
 *
 * @author alejandro
 */
public enum DuracionAnuncio {
    
    UN_DIA(1),
    TRES_DIAS(3),
    UNA_SEMANA(7),
    DOS_SEMANAS(14),;
    
    private int dias;

    private DuracionAnuncio(int dias) {
        this.dias = dias;
    }
    
    public int getDias() {
        return dias;
    }
    
    
    
}
