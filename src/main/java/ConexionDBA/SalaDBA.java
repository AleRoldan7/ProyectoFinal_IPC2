/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import ModeloEntidad.Cine.Sala;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alejandro
 */
public class SalaDBA {

    private static final String CREAR_SALA_QUERY = "INSERT INTO sala (id_usuario, nombre_sala, asiento_fila, asiento_columna, fecha_creacion) "
            + "VALUES (?,?,?,?,?)";

    private static final String ENCONTRAR_SALA_QUERY = "SELECT * FROM sala WHERE nombre_sala = ?";
    
    public void crearSala(Sala sala) {
        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement insert = connection.prepareStatement(CREAR_SALA_QUERY)) {

            insert.setInt(1, sala.getIdUsuario());
            insert.setString(2, sala.getNombreSala());
            insert.setInt(3, sala.getFilaSala());
            insert.setInt(4, sala.getColumnaSala());
            insert.setDate(5, Date.valueOf(sala.getFechaCreacion()));
            insert.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean existeSala(String nombreSala) {

        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement query = connection.prepareStatement(ENCONTRAR_SALA_QUERY)) {

            query.setString(1, nombreSala);
            ResultSet resultSet = query.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
