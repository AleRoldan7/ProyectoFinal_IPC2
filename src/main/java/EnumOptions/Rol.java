/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EnumOptions;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 *
 * @author alejandro
 */
public enum Rol {
    
    USUARIO_COMUN,
    ANUNCIANTE,
    ADMIN_CINE,
    ADMIN_SISTEMA;
    
    @JsonCreator
    public static Rol fromString(String key) {
        if (key == null) return null;
        switch (key.toUpperCase()) {
            case "USUARIO_COMUN": return USUARIO_COMUN;
            case "ANUNCIANTE": return ANUNCIANTE;
            case "ADMIN_CINE": return ADMIN_CINE;
            case "ADMIN_SISTEMA": return ADMIN_SISTEMA;
            default: throw new IllegalArgumentException("Rol desconocido: " + key);
        }
    }
}
