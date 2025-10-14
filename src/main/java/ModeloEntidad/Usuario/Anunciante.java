/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Usuario;

import EnumOptions.Rol;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class Anunciante extends Usuario {

    public Anunciante(String userName, String password) {
        super(userName, password, Rol.ANUNCIANTE);
    }
}
