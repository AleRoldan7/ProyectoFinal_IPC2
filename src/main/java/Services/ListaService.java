/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ListasDBA;
import Dtos.Anuncio.AnuncioResponse;
import Dtos.Anuncio.NewAnuncioRequest;
import EnumOptions.Rol;
import ModeloEntidad.Anuncio.Anuncio;
import ModeloEntidad.Cine.Cine;
import ModeloEntidad.Cine.Pelicula;
import ModeloEntidad.Cine.Sala;
import ModeloEntidad.Usuario.Usuario;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    
    public List<Pelicula> obtenerPeliculas() {

        return listasDBA.listaPeliculas();
    }
    
    
    public List<Sala> obtenerSalaAdmin(Integer idUsuario) {

        return listasDBA.listaSalasAdmin(idUsuario);
    }

    public List<Sala> obtenerSala() {

        return listasDBA.listaSala();
    }

    public List<NewAnuncioRequest> obtenerAnuncios(Integer idUsuario) {

        return listasDBA.listaAnuncios(idUsuario);
    }

    public InputStream obtenerImagen(Integer idUsuario) {

        return listasDBA.obtenerImagenPorId(idUsuario);
    }

    public List<Sala> obtenerSalaCine(Integer idCine) {

        return listasDBA.listaSalaCine(idCine);
    }

    public List<Cine> obtenerCines() {

        return listasDBA.listaCines();
    }

    public List<Map<String, Object>> obtenerPeliculasPorSalaYCine(int idSala, int idCine) {
        try {
            return listasDBA.obtenerPeliculasPorSalaYCine(idSala, idCine);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<AnuncioResponse> getAnuncioPorUsuario(int idUsuario) throws SQLException {
        
        return listasDBA.getAnunciosPorUsuario(idUsuario);
    }
}
