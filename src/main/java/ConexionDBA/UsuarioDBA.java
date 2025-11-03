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
    private static final String DESCONTAR_DINERO_ANUNCIO_QUERY = "UPDATE usuario SET dinero_cartera = dinero_cartera - ? WHERE id_usuario = ? "
            + "AND dinero_cartera >= ?";

    public void agregarUsuario(Usuario usuario) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(CREAR_USUARIO_QUERY)) {

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

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(AGREGAR_DINERO_QUERY)) {

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

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(ENCONTRAR_USUARIO_QUERY)) {

            query.setString(1, userName);

            try (ResultSet resultSet = query.executeQuery();) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Usuario verificarUsuario(String userName, String password) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(VERIFICAR_USUARIO_QUERY)) {

            query.setString(1, userName);
            query.setString(2, password);

            System.out.println(userName);
            System.out.println(password);

            try (ResultSet resultSet = query.executeQuery()) {

                if (resultSet.next()) {

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultSet.getInt("id_usuario"));
                    usuario.setNombre(resultSet.getString("nombre"));
                    usuario.setUserName(resultSet.getString("user_name"));
                    usuario.setPassword(resultSet.getString("password"));
                    usuario.setRolUsuario(Rol.valueOf(resultSet.getString("rol_usuario")));
                    usuario.setDineroCartera(resultSet.getDouble("dinero_cartera"));
                    usuario.setFechaRegistro(resultSet.getDate("fecha_registro").toLocalDate());

                    return usuario;

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Usuario obtenerUsuario(String userName) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(ENCONTRAR_USUARIO_QUERY)) {

            query.setString(1, userName);

            try (ResultSet resultSet = query.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultSet.getInt("id_usuario"));
                    usuario.setNombre(resultSet.getString("nombre"));
                    usuario.setUserName(resultSet.getString("user_name"));
                    usuario.setPassword(resultSet.getString("password"));
                    usuario.setRolUsuario(Rol.valueOf(resultSet.getString("rol_usuario")));
                    usuario.setDineroCartera(resultSet.getDouble("dinero_cartera"));
                    usuario.setFechaRegistro(resultSet.getDate("fecha_registro").toLocalDate());
                    return usuario;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean comprarAnuncio(int idUsuario, double dinero) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(DESCONTAR_DINERO_ANUNCIO_QUERY)) {

            query.setDouble(1, dinero);
            query.setInt(2, idUsuario);
            query.setDouble(3, dinero);

            int fila = query.executeUpdate();
            System.out.println("Se desconto el dinero " + dinero);
            return fila > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario obtenerUsuarioPorId(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(sql)) {

            query.setInt(1, idUsuario);

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setUserName(rs.getString("user_name"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setRolUsuario(Rol.valueOf(rs.getString("rol_usuario")));
                    usuario.setDineroCartera(rs.getDouble("dinero_cartera"));
                    usuario.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                    return usuario;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean descontarDineroUsuario(int idUsuario, double monto) {
        String sql = "UPDATE usuario SET dinero_cartera = dinero_cartera - ? "
                + "WHERE id_usuario = ? AND dinero_cartera >= ?";

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, monto);
            ps.setInt(2, idUsuario);
            ps.setDouble(3, monto);

            int filas = ps.executeUpdate();
            return filas > 0; 

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
