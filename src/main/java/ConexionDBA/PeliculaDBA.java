/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

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
    private static final String ASIGNAR_PELICULA_SALA_QUERY = "INSERT INTO pelicula_sala_cine (id_sala_cine, id_pelicula) VALUES (?,?)";

    public void agregarPelicula(Pelicula pelicula) {

        try (Connection connection = Conexion.getInstance().getConnect();
                PreparedStatement insert = connection.prepareStatement(AGREGAR_PELICULA_QUERY)) {

            insert.setString(1, pelicula.getTituloPelicula());
            insert.setString(2, pelicula.getSinopsisPelicula());
            insert.setTime(3, Time.valueOf(pelicula.getDuracionPelicula()));
            insert.setString(4, pelicula.getCastPelicula());
            insert.setString(5, pelicula.getDirectorPelicula());
            insert.setBytes(6, pelicula.getPosterPelicula());
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

    public Integer obtenerIdSalaCine(Integer idSala) {
        
        String query = "SELECT id_sala_cine FROM sala_cine WHERE id_sala = ?";
        try (Connection connection = Conexion.getInstance().getConnect();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idSala);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_sala_cine");
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
}
