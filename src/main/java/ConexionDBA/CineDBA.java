/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Cine.UpdateCineRequest;
import ModeloEntidad.Cine.Cine;
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
public class CineDBA {

    private static final String CREAR_CINE_QUERY = "INSERT INTO cine (nombre_cine, id_usuario, fecha_creacion) VALUES (?,?,?)";
    private static final String ENCONTRAR_CINE_QUERY = "SELECT * FROM cine WHERE nombre_cine = ?";
    private static final String EXISTE_CINEID_QUERY = "SELECT * FROM cine WHERE id_cine = ?";
    private static final String VERIFICAR_USUARIO_CINE_QUERY = "SELECT * FROM cine WHERE id_usuario = ?";
    private static final String OBTENER_CINE_ADMINISTRADOR_QUERY = "SELECT id_cine FROM cine WHERE id_usuario = ?";
    private static final String ACTUALIZAR_CINE_QUERY = "UPDATE cine SET nombre_cine = ?, id_usuario = ?, fecha_creacion = ?, costo_cine = ? "
            + "WHERE id_cine = ?";
    private static final String ELIMINAR_CINE_QUERY = "DELETE FROM cine WHERE id_cine = ?";

    public void crearCine(Cine cine) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(CREAR_CINE_QUERY)) {

            insert.setString(1, cine.getNombreCine());
            insert.setInt(2, cine.getIdUsuario());
            insert.setDate(3, Date.valueOf(cine.getFechaCreacion()));
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeCine(String nombreCine) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(ENCONTRAR_CINE_QUERY)) {

            query.setString(1, nombreCine);

            try (ResultSet resultSet = query.executeQuery();) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeCineID(Integer idCine) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(EXISTE_CINEID_QUERY)) {

            query.setInt(1, idCine);
            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean adminAsigandoCine(int idUsuario) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(VERIFICAR_USUARIO_CINE_QUERY)) {

            query.setInt(1, idUsuario);

            try (ResultSet resultSet = query.executeQuery();) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer obtenerIdCineAdmin(int idUsuario) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(OBTENER_CINE_ADMINISTRADOR_QUERY)) {

            query.setInt(1, idUsuario);

            try (ResultSet resultSet = query.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_cine");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarCine(UpdateCineRequest updateCineRequest) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CINE_QUERY)) {

            update.setString(1, updateCineRequest.getNombreCine());
            update.setInt(2, updateCineRequest.getIdUsuario());
            update.setDate(3, Date.valueOf(updateCineRequest.getFechaCreacion()));
            update.setDouble(4, updateCineRequest.getCostoCine());
            update.setInt(5, updateCineRequest.getIdCine());
            update.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCine(int idCine) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement delete = connection.prepareStatement(ELIMINAR_CINE_QUERY)) {

            delete.setInt(1, idCine);
            delete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean esAdminDelCine(int idCine, int idUsuario) {
        String sql = "SELECT id_cine FROM cine WHERE id_cine = ? AND id_usuario = ?";

        System.out.println("SQL: " + sql);
        System.out.println("ID Cine: " + idCine);
        System.out.println("ID Usuario: " + idUsuario);

        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCine);
            ps.setInt(2, idUsuario);

            ResultSet rs = ps.executeQuery();
            boolean resultado = rs.next();

            System.out.println("Resultado de la consulta: " + resultado);

            if (resultado) {
                int idCineEncontrado = rs.getInt("id_cine");
                System.out.println("ID Cine encontrado en BD: " + idCineEncontrado);
            } else {
                System.out.println("No se encontró relación cine-usuario");
            }

            return resultado;

        } catch (SQLException e) {
            System.out.println("Error en esAdminDelCine: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public double obtenerCostoBloqueo(int idCine) {
        String sql = "SELECT costo_por_dia FROM costo_bloqueo_anuncio WHERE id_cine = ?";
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCine);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getDouble(1) : 5.00;
        } catch (SQLException e) {
            return 5.00;
        }
    }

    public double obtenerSaldoCartera(int idCine) {
        String sql = "SELECT dinero_cartera FROM cine WHERE id_cine = ?";
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCine);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getDouble(1) : 0.0;
        } catch (SQLException e) {
            return 0.0;
        }
    }

    public void recargarCartera(int idCine, double monto) {
        String sql = "UPDATE cine SET dinero_cartera = dinero_cartera + ? WHERE id_cine = ?";
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, monto);
            ps.setInt(2, idCine);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void descontarCartera(int idCine, double monto) {
        String sql = "UPDATE cine SET dinero_cartera = dinero_cartera - ? WHERE id_cine = ?";
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, monto);
            ps.setInt(2, idCine);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearBloqueo(int idCine, int dias, LocalDate inicio, LocalDate fin, double monto) {
        String sql = "INSERT INTO bloqueo_anuncio (id_cine, dias_bloqueo, fecha_inicio, fecha_fin, monto_pagado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCine);
            ps.setInt(2, dias);
            ps.setDate(3, java.sql.Date.valueOf(inicio));
            ps.setDate(4, java.sql.Date.valueOf(fin));
            ps.setDouble(5, monto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean tieneBloqueoActivo(int idCine) {
        String sql = "SELECT 1 FROM bloqueo_anuncio WHERE id_cine = ? AND CURDATE() BETWEEN fecha_inicio AND fecha_fin";
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCine);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    public void registrarBloqueo(int idCine, int dias, LocalDate inicio, LocalDate fin, double monto) {
        String sql = "INSERT INTO bloqueo_anuncio (id_cine, dias_bloqueo, fecha_inicio, fecha_fin, monto_pagado) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCine);
            ps.setInt(2, dias);
            ps.setDate(3, java.sql.Date.valueOf(inicio));
            ps.setDate(4, java.sql.Date.valueOf(fin));
            ps.setDouble(5, monto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al registrar bloqueo");
        }
    }
}
