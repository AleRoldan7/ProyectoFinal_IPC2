/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Usuario;

import EnumOpciones.Rol;

/**
 *
 * @author alejandro
 */
public class AdminSistema extends Usuario{

    public AdminSistema(String userName, String password) {
        super(userName, password, Rol.Admin_Sistema);
    }
    
    
}
