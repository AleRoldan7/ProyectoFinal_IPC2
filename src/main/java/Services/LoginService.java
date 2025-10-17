/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.UsuarioDBA;
import EnumOptions.Rol;
import Excepciones.DatosInvalidos;
import ModeloEntidad.Usuario.Usuario;

/**
 *
 * @author alejandro
 */
public class LoginService {
    
    public Usuario loginUsuario(String userName, String password) throws DatosInvalidos {
        UsuarioDBA usuarioDBA = new UsuarioDBA();
        return usuarioDBA.verificarUsuario(userName, password);
        
      
    }
}
