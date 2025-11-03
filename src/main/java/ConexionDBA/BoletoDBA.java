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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
public class BoletoDBA {

    private static final String COMPRA_BOLETO_QUERY = "INSERT INTO boleto_compra (id_usuario, id_pelicula_sala, precio_boleto, fila_asiento,"
            + "columna_asiento, fecha_compra) VALUES (?,?,?,?,?,?)";
    private static final String ASIENTOS_OCUPADOS_QUERY = "SELECT fila_asiento, columna_asiento FROM boleto_compra WHERE id_pelicula_sala = ?";
    private static final String PELICULAS_POR_CINE_QUERY
            = "SELECT "
            + "psc.id_pelicula_sala, "
            + "p.id_pelicula, "
            + "p.titulo_pelicula, "
            + "s.nombre_sala, "
            + "c.nombre_cine "
            + "FROM pelicula_sala_cine psc "
            + "JOIN sala_cine sc ON psc.id_sala_cine = sc.id_sala_cine "
            + "JOIN sala s ON sc.id_sala = s.id_sala "
            + "JOIN cine c ON sc.id_cine = c.id_cine "
            + "JOIN pelicula p ON psc.id_pelicula = p.id_pelicula "
            + "WHERE c.id_cine = ?";

    private static final String EXISTE_BOLETOID_QUERY = "SELECT * FROM boleto WHERE id_boleto = ?";
    private static final String ACTUALIZAR_BOLETO_QUERY = "UPDATE boleto SET id_pelicula_sala = ?, fila_asiento = ?, columna_asiento = ? "
            + "WHERE id_boleto = ?";  
    private static final String ELIMINAR_BOLETO_QUERY = "DELETE FROM boleto WHERE id_boleto = ?";
    
    public void comprarBoleto(Boleto boleto) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(COMPRA_BOLETO_QUERY)) {

            insert.setInt(1, boleto.getIdUsuario());
            insert.setInt(2, boleto.getIdPeliculaSala());
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

    public List<Map<String, Object>> obtenerPeliculasPorCine(int idCine) throws SQLException {
        List<Map<String, Object>> peliculas = new ArrayList<>();

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(PELICULAS_POR_CINE_QUERY)) {

            query.setInt(1, idCine);
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> pelicula = new HashMap<>();
                pelicula.put("idPeliculaSala", resultSet.getInt("id_pelicula_sala"));
                pelicula.put("idPelicula", resultSet.getInt("id_pelicula"));
                pelicula.put("tituloPelicula", resultSet.getString("titulo_pelicula"));
                pelicula.put("nombreSala", resultSet.getString("nombre_sala"));
                pelicula.put("nombreCine", resultSet.getString("nombre_cine"));
                peliculas.add(pelicula);
            }

        }

        return peliculas;
    }
    
    public boolean existeBoletoId(Integer idBoleto) {
        
        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement query = connection.prepareStatement(EXISTE_BOLETOID_QUERY)) {

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

        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement update = connection.prepareStatement(ACTUALIZAR_BOLETO_QUERY)) {

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

        try (Connection connection = Conexion.getInstance().getConnect(); 
                PreparedStatement delete = connection.prepareStatement(ELIMINAR_BOLETO_QUERY)) {

            delete.setInt(1, idBoleto);
            delete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
