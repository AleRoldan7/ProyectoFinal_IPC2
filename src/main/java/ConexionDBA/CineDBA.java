/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import ModeloEntidad.Cine.Cine;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alejandro
 */
public class CineDBA {

    private static final String CREAR_CINE_QUERY = "INSERT INTO cine (nombre_cine, id_usuario, fecha_creacion) VALUES (?,?,?)";
    private static final String ENCONTRAR_CINE_QUERY = "SELECT * FROM cine WHERE nombre_cine = ?";
    private static final String VERIFICAR_USUARIO_CINE_QUERY = "SELECT * FROM cine WHERE id_usuario = ?";

    public void crearCine(Cine cine) {

        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement insert = connection.prepareStatement(CREAR_CINE_QUERY)) {

            insert.setString(1, cine.getNombreCine());
            insert.setInt(2, cine.getIdUsuario());
            insert.setDate(3, Date.valueOf(cine.getFechaCreacion()));
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeCine(String nombreCine) {

        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement query = connection.prepareStatement(ENCONTRAR_CINE_QUERY)) {

            query.setString(1, nombreCine);
            ResultSet resultSet = query.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean adminAsigandoCine(int idUsuario){
        
        Connection connection = Conexion.getInstance().getConnect();
        
        try (PreparedStatement query = connection.prepareStatement(VERIFICAR_USUARIO_CINE_QUERY)){
            
            query.setInt(1, idUsuario);
            ResultSet resultSet = query.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
