/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import ModeloEntidad.Usuario.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author alejandro
 */
public class UsuarioDBA {
    
    
    private static final String CREAR_USUARIO_QUERY = "INSERT INTO usuario (nombre, user_name, password, rol_usuario, fecha_registro)"
            + "VALUES (?,?,?,?,?)";
    
    private static final String AGREGAR_DINERO_QUERY = "UPDATE usuario SET dinero_cartera = dinero_cartera + ? WHERE user_name";
     
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
    
    public void recargarCartera(String userName, double dineroCartera){
        
        Connection connection = Conexion.getInstance().getConnect();
        
        try (PreparedStatement insert = connection.prepareStatement(AGREGAR_DINERO_QUERY)){
            
            insert.setString(1, userName);
            insert.setDouble(2, dineroCartera);
            int fila = insert.executeUpdate();
            
            if (fila > 0) {
                System.out.println("Se agrego dinero");
            } else {
                System.out.println("No se agrego nada");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
