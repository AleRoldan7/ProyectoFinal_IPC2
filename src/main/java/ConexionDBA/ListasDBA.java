/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import EnumOptions.Rol;
import ModeloEntidad.Cine.Sala;
import ModeloEntidad.Usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<Usuario> listaUsuarios() {

        List<Usuario> usuarios = new ArrayList<>();
        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement query = connection.prepareStatement(LISTA_USUARIOS_QUERY)) {

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
        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement query = connection.prepareStatement(LISTA_ADMIN_CINE_QUERY)) {

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
        Connection connection = Conexion.getInstance().getConnect();

        try (PreparedStatement query = connection.prepareStatement(LISTA_SALAS_QUERY)) {

            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Sala sala = new Sala(
                        resultSet.getInt("id_sala"),
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("nombre_sala"),
                        resultSet.getInt("asiento_fila"),
                        resultSet.getInt("asiento_columna"),
                        LocalDate.parse(resultSet.getString("fecha_registro"))
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
}
