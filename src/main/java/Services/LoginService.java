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
    
    public Usuario loginUsuario(String userName, String password, Rol rol) throws DatosInvalidos {
        UsuarioDBA usuarioDBA = new UsuarioDBA();
        
        Usuario usuario = usuarioDBA.verificarUsuario(userName, password, rol);
        
        if (usuario == null) {
            throw new DatosInvalidos("El usuario no existe");
            
        } 
        
        if (!usuario.getPassword().equals(password) && !usuario.getRolUsuario().equals(rol)) {
            throw new DatosInvalidos("Contraseña o Rol incorrectos");
        }
        
        return usuario;
    }
}
