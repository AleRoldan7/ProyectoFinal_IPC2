/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Pelicula.UpdatePeliculaRequest;
import ModeloEntidad.Cine.Pelicula;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 *
 * @author alejandro
 */
public class PeliculaDBA {

    private static final String AGREGAR_PELICULA_QUERY = "INSERT INTO pelicula (titulo_pelicula, sinopsis, duracion, cast_pelicula, "
            + "director_pelicula, poster_pelicula) VALUES (?,?,?,?,?,?)";

    private static final String EXISTE_PELICULA_QUERY = "SELECT * FROM pelicula WHERE titulo_pelicula = ?";
    private static final String EXISTE_PELICULAID_QUERY = "SELECT * FROM pelicula WHERE id_pelicula = ?";
    private static final String ASIGNAR_PELICULA_SALA_QUERY = "INSERT INTO pelicula_sala_cine (id_sala_cine, id_pelicula) VALUES (?,?)";
    private static final String OBTENER_SALA_CINE = "SELECT id_sala_cine FROM sala_cine WHERE id_sala = ?";
    private static final String ACTUALIZAR_PELICULA_QUERY = "UPDATE pelicula SET titulo_pelicula = ?, sinopsis = ?, duracion = ?, "
            + "cast_pelicula = ?, director_pelicula = ?, poster_pelicula = ? WHERE id_pelicula = ?";
    private static final String ELIMINAR_PELICULA_QUERY = "DELETE FROM pelicula WHERE id_pelicula = ?";

    public void agregarPelicula(Pelicula pelicula) {

        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement insert = connection.prepareStatement(AGREGAR_PELICULA_QUERY)) {

            insert.setString(1, pelicula.getTituloPelicula());
            insert.setString(2, pelicula.getSinopsisPelicula());
            insert.setTime(3, Time.valueOf(pelicula.getDuracionPelicula()));
            insert.setString(4, pelicula.getCastPelicula());
            insert.setString(5, pelicula.getDirectorPelicula());
            insert.setBinaryStream(6, pelicula.getPosterPelicula());
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existePelicula(String tituloPelicula) {

        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement query = connection.prepareStatement(EXISTE_PELICULA_QUERY)) {

            query.setString(1, tituloPelicula);
            ResultSet resultSet = query.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existePeliculaPorId(Integer idPelicula) {
        
        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement query = connection.prepareStatement(EXISTE_PELICULAID_QUERY)) {

            query.setInt(1, idPelicula);
            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer obtenerIdSalaCine(Integer idSala) {

        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement query = connection.prepareStatement(OBTENER_SALA_CINE)) {

            query.setInt(1, idSala);
            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id_sala_cine");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void asignarPeliculaSala(Integer idSalaCine, Integer idPelicula) {

        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement insert = connection.prepareStatement(ASIGNAR_PELICULA_SALA_QUERY)) {

            insert.setInt(1, idSalaCine);
            insert.setInt(2, idPelicula);
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarPelicula(UpdatePeliculaRequest updatePeliculaRequest) {

        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_PELICULA_QUERY)) {

            update.setString(1, updatePeliculaRequest.getTituloPelicula());
            update.setString(2, updatePeliculaRequest.getSinopsisPelicula());
            update.setTime(3, Time.valueOf(updatePeliculaRequest.getDuracionPelicula()));
            update.setString(4, updatePeliculaRequest.getCastPelicula());
            update.setString(5, updatePeliculaRequest.getDirectorPelicula());
            update.setBinaryStream(6, updatePeliculaRequest.getPosterPelicula());
            update.setInt(7, updatePeliculaRequest.getIdPelicula());
            update.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPelicula(int idPelicula) {

        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_PELICULA_QUERY)) {

            delete.setInt(1, idPelicula);
            delete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
