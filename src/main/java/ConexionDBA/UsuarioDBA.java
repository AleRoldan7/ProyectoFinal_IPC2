/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import ModeloEntidad.Usuario.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alejandro
 */
public class UsuarioDBA {
    
    
    private static final String CREAR_USUARIO_QUERY = "INSERT INTO usuario (nombre, user_name, password, rol_usuario, fecha_registro)"
            + "VALUES (?,?,?,?,?)";
    
    private static final String ENCONTRAR_USUARIO_QUERY = "SELECT * FROM usuario WHERE user_name = ?";
    
     
    public void agregarUsuario(Usuario usuario){
        
        Connection connection = Conexion.getInstance().getConnect();
        
        try (PreparedStatement insert = connection.prepareStatement(CREAR_USUARIO_QUERY)){
            
            insert.setString(1, usuario.getNombre());
            insert.setString(2, usuario.getUserName());
            insert.setString(3, usuario.getPassword());
            insert.setString(4, usuario.getRolUsuario().name());
            insert.setDate(5, Date.valueOf(usuario.getFechaRegistro()));
            insert.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public boolean existeUsuario(String userName) {

        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement query = connection.prepareStatement(ENCONTRAR_USUARIO_QUERY)) {

            query.setString(1, userName);
            ResultSet resultSet = query.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
