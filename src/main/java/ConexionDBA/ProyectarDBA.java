/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Proyeccion.NewProyeccionRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 *
 * @author alejandro
 */
public class ProyectarDBA {
    
    
    private static final String ASIGNAR_PROYECCION_QUERY = "INSERT INTO proyecta_pelicula (id_sala_cine, id_pelicula, hora, fecha_inicio, "
            + "fecha_fin, precio) VALUES (?,?,?,?,?,?)";
    
    private static final String OBTENER_PROYECCIONES_ADMIN_QUERY = "SELECT p.* pe.titulo_pelicula, s.nombre_sala, c.nombre_cine "
            + "FROM proyecta_pelicula p "
            + "JOIN pelicula pe ON p.id_pelicula = pe.id_pelicula "
            + "JOIN sala_cine sc.id_sala = s.id_sala "
            + "JOIN sala s ON  sc.id_sala = s.id_sala "
            + "JOIN cine c ON sc.id_cine = c.id_cine "
            + "WHERE c.id_usuario = ? "
            + "ORDER BY p.fecha_inicio DESC, p.hora";
    
    public void asignarPeliculaSala(NewProyeccionRequest newProyeccionRequest, int idUsuario) throws SQLException {
        
        SalaDBA salaDBA = new SalaDBA();
        Integer idSalaCine = salaDBA.obtenerIdSalaCinePorSalaYAdmin(newProyeccionRequest.getIdSala(), idUsuario);
        
        if (idSalaCine == null) {
            
            throw new SQLException("La sala no pertenece al administrador");
        }
        
        try (Connection connection = Conexion.getInstance().getConnect();
                PreparedStatement insert = connection.prepareStatement(ASIGNAR_PROYECCION_QUERY)){
            
            insert.setInt(1, idSalaCine);
            insert.setInt(2, newProyeccionRequest.getIdPelicula());
            insert.setTime(3, Time.valueOf(newProyeccionRequest.getHora()));
            insert.setDate(4, Date.valueOf(newProyeccionRequest.getFechaInicio()));
            insert.setDate(5, Date.valueOf(newProyeccionRequest.getFechaFin()));
            insert.setDouble(6, newProyeccionRequest.getPrecio());
            insert.executeUpdate();
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public ResultSet getProyeccionesAdmin(int idUsuario) throws SQLException {
        Connection connection = Conexion.getInstance().getConnect();
        PreparedStatement query = connection.prepareStatement(OBTENER_PROYECCIONES_ADMIN_QUERY);
        query.setInt(1, idUsuario);
        return query.executeQuery();
    }
    
}
