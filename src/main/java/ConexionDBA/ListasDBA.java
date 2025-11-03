/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Anuncio.NewAnuncioRequest;
import EnumOptions.Rol;
import EnumOptions.TipoAnuncio;
import ModeloEntidad.Anuncio.Anuncio;
import ModeloEntidad.Cine.Cine;
import ModeloEntidad.Cine.Pelicula;
import ModeloEntidad.Cine.Sala;
import ModeloEntidad.Usuario.Usuario;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
public class ListasDBA {

    private static final String LISTA_USUARIOS_QUERY = "SELECT * FROM usuario";
    private static final String LISTA_ADMIN_CINE_QUERY = "SELECT * FROM usuario WHERE rol_usuario = 'ADMIN_CINE'";
    private static final String LISTA_CINES_QUERY = "SELECT * FROM cine";
    private static final String LISTA_SALAS_QUERY = "SELECT * FROM sala";
    private static final String LISTA_SALAS_ADMIN_QUERY = "SELECT * FROM sala WHERE id_usuario = ?";
    private static final String LISTA_PELICULAS_QUERY = "SELECT * FROM pelicula";
    private static final String LISTA_ANUNCIOS_QUERY = "SELECT * FROM anuncio WHERE nombre_anunciante = ?";
    private static final String LISTA_SALA_CINE_QUERY
            = "SELECT s.* FROM sala s "
            + "INNER JOIN sala_cine sc ON s.id_sala = sc.id_sala "
            + "WHERE sc.id_cine = ?";

    private static final String OBTENER_PELICULAS_QUERY =
        "SELECT psc.id_pelicula_sala, p.id_pelicula, p.titulo_pelicula, s.nombre_sala, c.nombre_cine " +
        "FROM pelicula_sala_cine psc " +
        "JOIN sala_cine sc ON psc.id_sala_cine = sc.id_sala_cine " +
        "JOIN sala s ON sc.id_sala = s.id_sala " +
        "JOIN cine c ON sc.id_cine = c.id_cine " +
        "JOIN pelicula p ON psc.id_pelicula = p.id_pelicula " +
        "WHERE sc.id_sala = ? AND sc.id_cine = ?";
    
    public List<Usuario> listaUsuarios() {

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_USUARIOS_QUERY)) {

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("nombre"),
                        resultSet.getString("user_name"),
                        resultSet.getString("password"),
                        Rol.valueOf(resultSet.getString("rol_usuario")),
                        resultSet.getDouble("dinero_cartera"),
                        LocalDate.parse(resultSet.getString("fecha_registro"))
                );
                usuarios.add(usuario);
                System.out.println("Encontrados" + usuarios.size());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Usuarios:" + usuarios.size());
        return usuarios;
    }

    public List<Usuario> listaAdminCine() {

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_ADMIN_CINE_QUERY)) {

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("nombre"),
                        resultSet.getString("user_name"),
                        resultSet.getString("password"),
                        Rol.valueOf(resultSet.getString("rol_usuario")),
                        resultSet.getDouble("dinero_cartera"),
                        resultSet.getDate("fecha_registro").toLocalDate()
                );
                usuarios.add(usuario);
                System.out.println("Encontrados" + usuarios.size());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Usuarios:" + usuarios.size());
        return usuarios;

    }

    public List<Sala> listaSala() {

        List<Sala> salas = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_SALAS_QUERY)) {

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Sala sala = new Sala(
                        resultSet.getInt("id_sala"),
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("nombre_sala"),
                        resultSet.getInt("asiento_fila"),
                        resultSet.getInt("asiento_columna"),
                        LocalDate.parse(resultSet.getString("fecha_creacion"))
                );
                salas.add(sala);
                System.out.println("Encontradas" + salas.size());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Salas:" + salas.size());
        return salas;
    }

    public List<Pelicula> listaPeliculas() {

        List<Pelicula> peliculas = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_PELICULAS_QUERY)) {

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Pelicula pelicula = new Pelicula(
                        resultSet.getInt("id_pelicula"),
                        resultSet.getString("titulo_pelicula"),
                        resultSet.getString("sinopsis"),
                        LocalTime.parse(resultSet.getString("duracion")),
                        resultSet.getString("cast_pelicula"),
                        resultSet.getString("director_pelicula"),
                        resultSet.getBinaryStream("poster_pelicula")
                );
                peliculas.add(pelicula);
                System.out.println("Encontradas" + peliculas.size());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Salas:" + peliculas.size());
        return peliculas;
    }

    public List<Sala> listaSalasAdmin(Integer idUsuario) {

        List<Sala> salas = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_SALAS_ADMIN_QUERY)) {

            query.setInt(1, idUsuario);
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Sala sala = new Sala(
                        resultSet.getInt("id_sala"),
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("nombre_sala"),
                        resultSet.getInt("asiento_fila"),
                        resultSet.getInt("asiento_columna"),
                        LocalDate.parse(resultSet.getString("fecha_creacion"))
                );
                salas.add(sala);
                System.out.println("Encontradas" + salas.size());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Salas:" + salas.size());
        return salas;
    }

    public List<NewAnuncioRequest> listaAnuncios(Integer idUsuario) {

        List<NewAnuncioRequest> anuncios = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_ANUNCIOS_QUERY)) {

            query.setInt(1, idUsuario);

            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    NewAnuncioRequest anuncio = new NewAnuncioRequest();
                    anuncio.setTipoAnuncio(TipoAnuncio.valueOf(resultSet.getString("tipo_anuncio")));
                    anuncio.setMensajeAnuncio(resultSet.getString("mensaje_anuncio"));
                    anuncio.setVideoAnuncio("video_anuncio");
                    anuncio.setNombreAnunciante(resultSet.getInt("nombre_anunciante"));
                    anuncio.setFechaInicio(resultSet.getDate("fecha_inicio").toLocalDate());
                    anuncio.setDiasVigencia(resultSet.getInt("dias_vigente"));
                    anuncio.setPrecio(resultSet.getDouble("precio"));

                    anuncios.add(anuncio);
                    System.out.println("ENcontrados" + anuncios.size());
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Salas:" + anuncios.size());
        return anuncios;
    }

    public InputStream obtenerImagenPorId(Integer idAnuncio) {
        String query = "SELECT imagen_anuncio FROM anuncio WHERE nombre_anunciante = ?";

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idAnuncio);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Blob blob = rs.getBlob("imagen_anuncio");
                    if (blob != null) {
                        return blob.getBinaryStream();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cine> listaCines() {

        List<Cine> cines = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_CINES_QUERY)) {

            
            
            try (ResultSet resultset = query.executeQuery()) {

                while (resultset.next()) {

                    Cine cine = new Cine(
                            resultset.getInt("id_cine"),
                            resultset.getString("nombre_cine"),
                            resultset.getInt("id_usuario"),
                            LocalDate.parse(resultset.getString("fecha_creacion")),
                            resultset.getDouble("costo_cine")
                    );

                    cines.add(cine);

                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error en mandar los datos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cines;

    }

    public List<Sala> listaSalaCine(Integer idCine) {

        List<Sala> salaCine = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(LISTA_SALA_CINE_QUERY)) {

            query.setInt(1, idCine);

            try (ResultSet resultSet = query.executeQuery()) {

                while (resultSet.next()) {

                    Sala sala = new Sala(
                            resultSet.getInt("id_sala"),
                            resultSet.getInt("id_usuario"),
                            resultSet.getString("nombre_sala"),
                            resultSet.getInt("asiento_fila"),
                            resultSet.getInt("asiento_columna"),
                            resultSet.getDate("fecha_creacion").toLocalDate()
                    );

                    salaCine.add(sala);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Sala en cine " + idCine + salaCine.size());
        return salaCine;
    }
    
    public List<Map<String, Object>> obtenerPeliculasPorSalaYCine(int idSala, int idCine) throws Exception {
        List<Map<String, Object>> peliculas = new ArrayList<>();

        try (Connection conn = Conexion.getInstance().getConnect();
             PreparedStatement stmt = conn.prepareStatement(OBTENER_PELICULAS_QUERY)) {

            stmt.setInt(1, idSala);
            stmt.setInt(2, idCine);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> pelicula = new HashMap<>();
                pelicula.put("idPeliculaSala", rs.getInt("id_pelicula_sala"));
                pelicula.put("idPelicula", rs.getInt("id_pelicula"));
                pelicula.put("tituloPelicula", rs.getString("titulo_pelicula"));
                pelicula.put("nombreSala", rs.getString("nombre_sala"));
                pelicula.put("nombreCine", rs.getString("nombre_cine"));
                peliculas.add(pelicula);
            }
        }

        return peliculas;
    }
}
