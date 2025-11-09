/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import ModeloEntidad.Anuncio.BloqueoAnuncio;
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
public class BloqueoAnuncioDBA {
    
    private static final String CREAR_BLOQUEO_QUERY = 
        "INSERT INTO bloqueo_anuncios (id_cine, dias_bloqueo, costo_total, fecha_inicio, fecha_fin, fecha_pago, activo) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String OBTENER_COSTO_BLOQUEO_QUERY = 
        "SELECT costo_bloqueo_diario FROM cine WHERE id_cine = ?";
    
    private static final String VERIFICAR_BLOQUEO_ACTIVO_QUERY = 
        "SELECT COUNT(*) FROM bloqueo_anuncios WHERE id_cine = ? AND activo = true AND fecha_fin >= ?";
    
    private static final String ACTUALIZAR_COSTO_BLOQUEO_QUERY = 
        "UPDATE cine SET costo_bloqueo_diario = ? WHERE id_cine = ?";
    
    private static final String DESCONTAR_DINERO_CINE_QUERY = 
        "UPDATE cine SET dinero_cartera = dinero_cartera - ? WHERE id_cine = ? AND dinero_cartera >= ?";

    public void crearBloqueo(BloqueoAnuncio bloqueo) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect();
             PreparedStatement insert = connection.prepareStatement(CREAR_BLOQUEO_QUERY)) {
            
            insert.setInt(1, bloqueo.getIdCine());
            insert.setInt(2, bloqueo.getDiasBloqueo());
            insert.setDouble(3, bloqueo.getCostoTotal());
            insert.setDate(4, Date.valueOf(bloqueo.getFechaInicio()));
            insert.setDate(5, Date.valueOf(bloqueo.getFechaFin()));
            insert.setDate(6, Date.valueOf(bloqueo.getFechaPago()));
            insert.setBoolean(7, bloqueo.isActivo());
            
            insert.executeUpdate();
        }
    }

    public double obtenerCostoBloqueoDiario(Integer idCine) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect();
             PreparedStatement query = connection.prepareStatement(OBTENER_COSTO_BLOQUEO_QUERY)) {
            
            query.setInt(1, idCine);
            
            try (ResultSet resultSet = query.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("costo_bloqueo_diario");
                }
            }
        }
        return 0.0;
    }

    public boolean tieneBloqueoActivo(Integer idCine) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect();
             PreparedStatement query = connection.prepareStatement(VERIFICAR_BLOQUEO_ACTIVO_QUERY)) {
            
            query.setInt(1, idCine);
            query.setDate(2, Date.valueOf(LocalDate.now()));
            
            try (ResultSet resultSet = query.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void actualizarCostoBloqueo(Integer idCine, double nuevoCosto) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect();
             PreparedStatement update = connection.prepareStatement(ACTUALIZAR_COSTO_BLOQUEO_QUERY)) {
            
            update.setDouble(1, nuevoCosto);
            update.setInt(2, idCine);
            
            update.executeUpdate();
        }
    }

    public boolean descontarDineroCine(Integer idCine, double monto) throws SQLException {
        try (Connection connection = Conexion.getInstance().getConnect();
             PreparedStatement update = connection.prepareStatement(DESCONTAR_DINERO_CINE_QUERY)) {
            
            update.setDouble(1, monto);
            update.setInt(2, idCine);
            update.setDouble(3, monto);
            
            int filas = update.executeUpdate();
            return filas > 0;
        }
    }
}
