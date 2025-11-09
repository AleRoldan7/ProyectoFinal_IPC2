/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Sala.UpdateSalaRequest;
import ModeloEntidad.Cine.Sala;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author alejandro
 */
public class SalaDBA {

    private static final String CREAR_SALA_QUERY = "INSERT INTO sala (id_usuario, nombre_sala, asiento_fila, asiento_columna, fecha_creacion) "
            + "VALUES (?,?,?,?,?)";

    private static final String ENCONTRAR_SALA_QUERY = "SELECT * FROM sala WHERE nombre_sala = ?";
    private static final String ASIGNAR_SALA_CINE_QUERY = "INSERT INTO sala_cine (id_sala, id_cine) VALUES (?,?)";
    private static final String OBTENER_IDSALACINE_QUERY = "SELECT id_sala_cine FROM sala_cine WHERE id_sala = ? AND id_cine = ?";
    private static final String EXISTE_SALAID_QUERY = "SELECT * FROM sala WHERE id_sala = ?";
    private static final String ACTUALIZAR_SALA_QUERY = "UPDATE sala SET nombre_sala = ?, asiento_fila = ?, asiento_columna = ?,"
            + "fecha_creacion = ? WHERE id_sala = ?";
    private static final String ELIMINAR_SALA_QUERY = "DELETE FROM sala WHERE id_sala = ?";

    private static final String OBTENER_SALA_ADMIN_QUERY = "SELECT sc.id_sala_cine "
            + "FROM sala_cine sc "
            + "JOIN cine c ON sc.id_cine = c.id_cine "
            + "WHERE sc.id_sala = ? AND c.id_usuario = ? ";

    public void crearSala(Sala sala) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(CREAR_SALA_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {

            insert.setInt(1, sala.getIdUsuario());
            insert.setString(2, sala.getNombreSala());
            insert.setInt(3, sala.getFilaSala());
            insert.setInt(4, sala.getColumnaSala());
            insert.setDate(5, Date.valueOf(sala.getFechaCreacion()));
            insert.executeUpdate();

            try (ResultSet resultSet = insert.getGeneratedKeys()) {
                if (resultSet.next()) {
                    sala.setIdSala(resultSet.getInt(1));
                } else {
                    throw new SQLException("No se pudo recuperar el id de la sala");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeSala(String nombreSala) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(ENCONTRAR_SALA_QUERY)) {

            query.setString(1, nombreSala);
            ResultSet resultSet = query.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeSalaID(Integer idSala) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(EXISTE_SALAID_QUERY)) {

            query.setInt(1, idSala);
            ResultSet resultSet = query.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void asignarSalaCine(Integer idSala, Integer idCine) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(ASIGNAR_SALA_CINE_QUERY)) {

            insert.setInt(1, idSala);
            insert.setInt(2, idCine);
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer obtenerIdSalaCine(Integer idSala, Integer idCine) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(OBTENER_IDSALACINE_QUERY)) {

            query.setInt(1, idSala);
            query.setInt(2, idCine);
            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt("id_sala_cine");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer obtenerIdSalaCinePorSalaYAdmin(Integer idSala, Integer idUsuarioAdmin) {

        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(OBTENER_SALA_ADMIN_QUERY)) {
            ps.setInt(1, idSala);
            ps.setInt(2, idUsuarioAdmin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_sala_cine");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarSala(UpdateSalaRequest updateSalaRequest) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement update = connection.prepareStatement(ACTUALIZAR_SALA_QUERY)) {

            update.setString(1, updateSalaRequest.getNombreSala());
            update.setInt(2, updateSalaRequest.getFilaSala());
            update.setInt(3, updateSalaRequest.getColumnaSala());
            update.setDate(4, Date.valueOf(updateSalaRequest.getFechaCreacion()));
            update.setInt(5, updateSalaRequest.getIdSala());
            update.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarSala(int idSala) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement delete = connection.prepareStatement(ELIMINAR_SALA_QUERY)) {

            delete.setInt(1, idSala);
            delete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
