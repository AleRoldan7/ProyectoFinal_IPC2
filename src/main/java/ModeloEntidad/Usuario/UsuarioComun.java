/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Usuario;

import EnumOptions.Rol;

/**
 *
 * @author alejandro
 */
public class UsuarioComun extends Usuario{

    public UsuarioComun(String userName, String password) {
        super(userName, password, Rol.USUARIO_COMUN);
    }
    
    
    
}
