/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import EnumOptions.Rol;
import ModeloEntidad.Usuario.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class UsuarioDBA {

    private static final String CREAR_USUARIO_QUERY = "INSERT INTO usuario (nombre, user_name, password, rol_usuario, fecha_registro)"
            + "VALUES (?,?,?,?,?)";

    private static final String AGREGAR_DINERO_QUERY = "UPDATE usuario SET dinero_cartera = dinero_cartera + ? WHERE user_name = ?";

    private static final String ENCONTRAR_USUARIO_QUERY = "SELECT * FROM usuario WHERE user_name = ?";
    private static final String VERIFICAR_USUARIO_QUERY = "SELECT * FROM usuario WHERE user_name = ? AND password = ?";


    
    public void agregarUsuario(Usuario usuario) {
        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement insert = connection.prepareStatement(CREAR_USUARIO_QUERY)) {

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



    public void recargarCartera(String userName, double dineroCartera) {

        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement insert = connection.prepareStatement(AGREGAR_DINERO_QUERY)) {

            insert.setDouble(1, dineroCartera);
            insert.setString(2, userName);
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

    public Usuario verificarUsuario(String userName, String password) {

        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement query = connection.prepareStatement(VERIFICAR_USUARIO_QUERY)) {

            query.setString(1, userName);
            query.setString(2, password);
            ResultSet resultSet = query.executeQuery();
            System.out.println(userName);
            System.out.println(password);

            if (resultSet.next()) {

                Usuario usuario = new Usuario();
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setUserName(resultSet.getString("user_name"));
                usuario.setPassword(resultSet.getString("password"));
                usuario.setRolUsuario(Rol.valueOf(resultSet.getString("rol_usuario")));
                usuario.setDineroCartera(resultSet.getDouble("dinero_cartera"));
                usuario.setFechaRegistro(resultSet.getDate("fecha_registro").toLocalDate());

                return usuario;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }
}
