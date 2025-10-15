/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.UsuarioDBA;
import Dtos.Usuario.NewUsuarioRequest;
import EnumOptions.Rol;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import ModeloEntidad.Usuario.Usuario;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class UsuarioService {

    public Usuario crearUsuario(NewUsuarioRequest newUsuarioRequest) throws DatosInvalidos, EntityExists {
        UsuarioDBA usuarioDBA = new UsuarioDBA();

        Usuario entidadUsuario = extraer(newUsuarioRequest);

        if (usuarioDBA.existeUsuario(entidadUsuario.getUserName())) {
            throw new EntityExists(
                    String.format("El usuario con user name %s ya existe", entidadUsuario.getUserName())
            );
        }

        usuarioDBA.agregarUsuario(entidadUsuario);

        return entidadUsuario;
    }

    private Usuario extraer(NewUsuarioRequest newUsuarioRequest) throws DatosInvalidos {
        
        try {
            Usuario entidadUsuario = new Usuario(
                    newUsuarioRequest.getNombre(), 
                    newUsuarioRequest.getUserName(), 
                    newUsuarioRequest.getPassword(), 
                    newUsuarioRequest.getRolUsuario(), 
                    newUsuarioRequest.getDineroCartera(), 
                    newUsuarioRequest.getFechaRegistro()
                   
            );

            if (!entidadUsuario.esValido()) {
                throw new DatosInvalidos("Error en los datos enviados");
            }

            return entidadUsuario;
        } catch (Exception e) {
            throw new DatosInvalidos("Error en los datos enviados" + e.getMessage());
        }

    }

}
