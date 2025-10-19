/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ListasDBA;
import EnumOptions.Rol;
import ModeloEntidad.Usuario.Usuario;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class ListaService {

    private ListasDBA listasDBA = new ListasDBA();

    public List<Usuario> obtnerDatosUsuario() {

        return listasDBA.listaUsuarios();
    }

    public List<Usuario> obtenerAdminCine() {
        
        return listasDBA.listaAdminCine();
        
    }
}
