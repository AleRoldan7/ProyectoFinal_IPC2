/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Boleto.UpdateBoletoRequest;
import ModeloEntidad.Cine.Boleto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
public class BoletoDBA {

    private static final String COMPRA_BOLETO_QUERY = "INSERT INTO boleto_compra (id_usuario, id_proyeccion, precio_boleto, fila_asiento,"
            + "columna_asiento, fecha_compra) VALUES (?,?,?,?,?,?)";
    private static final String ASIENTOS_OCUPADOS_QUERY = "SELECT fila_asiento, columna_asiento FROM boleto_compra WHERE id_proyeccion = ?";
    private static final String PELICULAS_POR_CINE_QUERY
            = "SELECT "
            + "    pp.id_proyecta AS idProyeccion, "
            + "    pp.hora, "
            + "    pp.precio, "
            + "    p.titulo_pelicula AS titulo, "
            + "    p.duracion, "
            + "    p.sinopsis, "
            + "    p.poster_pelicula AS poster, "
            + "    s.nombre_sala AS sala, "
            + "    s.asiento_fila AS filas, "
            + "    s.asiento_columna AS columnas, "
            + "    c.nombre_cine AS cine, "
            + "    c.id_cine AS idCine "
            + "FROM proyecta_pelicula pp "
            + "JOIN sala_cine sc ON pp.id_sala_cine = sc.id_sala_cine "
            + "JOIN pelicula p ON pp.id_pelicula = p.id_pelicula "
            + "JOIN sala s ON sc.id_sala = s.id_sala "
            + "JOIN cine c ON sc.id_cine = c.id_cine "
            + "WHERE CURRENT_DATE BETWEEN pp.fecha_inicio AND pp.fecha_fin "
            + "      AND (? IS NULL OR p.titulo_pelicula LIKE CONCAT('%', ?, '%')) "
            + "    ORDER BY c.nombre_cine, pp.hora";

    private static final String EXISTE_BOLETOID_QUERY = "SELECT * FROM boleto WHERE id_boleto = ?";
    private static final String ACTUALIZAR_BOLETO_QUERY = "UPDATE boleto SET id_pelicula_sala = ?, fila_asiento = ?, columna_asiento = ? "
            + "WHERE id_boleto = ?";
    private static final String ELIMINAR_BOLETO_QUERY = "DELETE FROM boleto WHERE id_boleto = ?";

    private static final String PELICULAS_COMPRADAS
            = "SELECT DISTINCT "
            + "    pp.id_proyecta AS idProyeccion, "
            + "    p.titulo_pelicula AS titulo, "
            + "    p.duracion, "
            + "    p.sinopsis, "
            + "    p.poster_pelicula AS poster, "
            + "    pp.hora, "
            + "    pp.precio, "
            + "    s.nombre_sala AS sala, "
            + "    c.nombre_cine AS cine "
            + "FROM boleto_compra b "
            + "JOIN proyecta_pelicula pp ON b.id_proyeccion = pp.id_proyecta "
            + "JOIN pelicula p ON pp.id_pelicula = p.id_pelicula "
            + "JOIN sala_cine sc ON pp.id_sala_cine = sc.id_sala_cine "
            + "JOIN sala s ON sc.id_sala = s.id_sala "
            + "JOIN cine c ON sc.id_cine = c.id_cine "
            + "WHERE b.id_usuario = ? "
            + "  AND CURRENT_DATE >= pp.fecha_inicio "
            + "ORDER BY pp.hora DESC";

    public void comprarBoleto(Boleto boleto) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(COMPRA_BOLETO_QUERY)) {

            insert.setInt(1, boleto.getIdUsuario());
            insert.setInt(2, boleto.getIdProyeccion());
            insert.setDouble(3, boleto.getPrecioBoleto());
            insert.setInt(4, boleto.getFilaAsiento());
            insert.setInt(5, boleto.getColumnaAsiento());
            insert.setDate(6, Date.valueOf(boleto.getFechaCompra()));
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Integer>> obtenerAsientosOcupados(int idPeliculaSala) throws SQLException {

        List<Map<String, Integer>> asientos = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(ASIENTOS_OCUPADOS_QUERY)) {

            query.setInt(1, idPeliculaSala);
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Map<String, Integer> asiento = new HashMap<>();
                asiento.put("fila", resultSet.getInt("fila_asiento"));
                asiento.put("columna", resultSet.getInt("columna_asiento"));
                asientos.add(asiento);
            }
        }
        return asientos;
    }

    public List<Map<String, Object>> obtenerPeliculasPorCine(String buscar) {
        List<Map<String, Object>> lista = new ArrayList<>();

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(PELICULAS_POR_CINE_QUERY)) {

            ps.setString(1, buscar);
            ps.setString(2, buscar);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("idProyeccion", rs.getInt("idProyeccion"));
                    m.put("hora", rs.getTime("hora"));  // TIME → String
                    m.put("precio", rs.getBigDecimal("precio"));
                    m.put("titulo", rs.getString("titulo"));
                    m.put("duracion", rs.getTime("duracion"));
                    m.put("sinopsis", rs.getString("sinopsis"));
                    byte[] posterBytes = rs.getBytes("poster");
                    String posterBase64 = (posterBytes != null)
                            ? java.util.Base64.getEncoder().encodeToString(posterBytes)
                            : null;
                    m.put("poster", posterBase64);
                    m.put("sala", rs.getString("sala"));
                    m.put("filas", rs.getInt("filas"));
                    m.put("columnas", rs.getInt("columnas"));
                    m.put("cine", rs.getString("cine"));
                    m.put("idCine", rs.getInt("idCine"));

                    lista.add(m);
                    System.out.println("PELÍCULA CARGADA: " + rs.getString("titulo") + " - " + rs.getTime("duracion") + " min");
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR EN QUERY CARTELERA:");
            e.printStackTrace();
        }
        System.out.println("TOTAL PELÍCULAS EN CARTELERA HOY: " + lista.size()); 
        return lista;
    }

    public boolean existeBoletoId(Integer idBoleto) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(EXISTE_BOLETOID_QUERY)) {

            query.setInt(1, idBoleto);
            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void actualizarBoleto(UpdateBoletoRequest updateBoletoRequest) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement update = connection.prepareStatement(ACTUALIZAR_BOLETO_QUERY)) {

            update.setInt(1, updateBoletoRequest.getIdPeliculaSala());
            update.setInt(2, updateBoletoRequest.getFilaAsiento());
            update.setInt(3, updateBoletoRequest.getColumnaAsiento());
            update.setInt(4, updateBoletoRequest.getIdBoleto());
            update.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarBoleto(int idBoleto) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement delete = connection.prepareStatement(ELIMINAR_BOLETO_QUERY)) {

            delete.setInt(1, idBoleto);
            delete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Map<String, Object>> obtenerPeliculasCompradas(int idUsuario) {
        List<Map<String, Object>> lista = new ArrayList<>();
        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(PELICULAS_COMPRADAS)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("idProyeccion", rs.getInt("idProyeccion"));
                    m.put("titulo", rs.getString("titulo"));
                    m.put("duracion", rs.getTime("duracion"));
                    m.put("sinopsis", rs.getString("sinopsis"));
                    m.put("hora", rs.getTime("hora"));
                    m.put("precio", rs.getBigDecimal("precio"));
                    m.put("sala", rs.getString("sala"));
                    m.put("cine", rs.getString("cine"));

                    byte[] poster = rs.getBytes("poster");
                    m.put("poster", poster != null ? Base64.getEncoder().encodeToString(poster) : null);

                    lista.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
