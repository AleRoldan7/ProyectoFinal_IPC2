/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import java.sql.Connection;
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
public class ComentarioDBA {
    
    private static final String AGREGAR_COMENTARIO_PELICULA = 
        "INSERT INTO comentario_pelicula (id_usuario, id_proyeccion, comentario, calificacion_pelicula) VALUES (?,?,?,?)";

    private static final String AGREGAR_COMENTARIO_SALA = 
        "INSERT INTO comentario_sala (id_usuario, id_sala_cine, comentario, calificacion_sala) VALUES (?,?,?,?)";

    private static final String VERIFICAR_BLOQUEO_SALA = 
        "SELECT bloqueada_comentarios FROM sala_cine WHERE id_sala_cine = ?";

    private static final String BLOQUEAR_COMENTARIOS = 
        "UPDATE sala_cine SET bloqueada_comentarios = ? WHERE id_sala_cine = ?";

    private static final String BLOQUEAR_VISIBILIDAD = 
        "UPDATE sala_cine SET bloqueada_visibilidad = ? WHERE id_sala_cine = ?";

    private static final String OBTENER_COMENTARIOS_PELICULA = 
        "SELECT cp.*, u.nombre AS usuario_nombre FROM comentario_pelicula cp " +
        "JOIN usuario u ON cp.id_usuario = u.id_usuario " +
        "WHERE cp.id_proyeccion = ? ORDER BY cp.fecha_comentario DESC";

    private static final String OBTENER_COMENTARIOS_SALA = 
        "SELECT cs.*, u.nombre AS usuario_nombre FROM comentario_sala cs " +
        "JOIN usuario u ON cs.id_usuario = u.id_usuario " +
        "WHERE cs.id_sala_cine = ? ORDER BY cs.fecha_comentario DESC";

    public void agregarComentarioPelicula(int idUsuario, int idProyeccion, String comentario, int calificacion) throws SQLException {
        try (Connection con = Conexion.getInstance().getConnect();
             PreparedStatement ps = con.prepareStatement(AGREGAR_COMENTARIO_PELICULA)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idProyeccion);
            ps.setString(3, comentario);
            ps.setInt(4, calificacion);
            ps.executeUpdate();
        }
    }

    public void agregarComentarioSala(int idUsuario, int idSalaCine, String comentario, int calificacion) throws SQLException {
        try (Connection con = Conexion.getInstance().getConnect();
             PreparedStatement check = con.prepareStatement(VERIFICAR_BLOQUEO_SALA)) {
            check.setInt(1, idSalaCine);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getBoolean("bloqueada_comentarios")) {
                throw new SQLException("Comentarios bloqueados para esta sala");
            }
        }

        try (Connection con = Conexion.getInstance().getConnect();
             PreparedStatement ps = con.prepareStatement(AGREGAR_COMENTARIO_SALA)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idSalaCine);
            ps.setString(3, comentario);
            ps.setInt(4, calificacion);
            ps.executeUpdate();
        }
    }

    public void bloquearComentariosSala(int idSalaCine, boolean bloquear) throws SQLException {
        try (Connection con = Conexion.getInstance().getConnect();
             PreparedStatement ps = con.prepareStatement(BLOQUEAR_COMENTARIOS)) {
            ps.setBoolean(1, bloquear);
            ps.setInt(2, idSalaCine);
            ps.executeUpdate();
        }
    }

    public void bloquearVisibilidadSala(int idSalaCine, boolean bloquear) throws SQLException {
        try (Connection con = Conexion.getInstance().getConnect();
             PreparedStatement ps = con.prepareStatement(BLOQUEAR_VISIBILIDAD)) {
            ps.setBoolean(1, bloquear);
            ps.setInt(2, idSalaCine);
            ps.executeUpdate();
        }
    }

    public List<Map<String, Object>> obtenerComentariosPelicula(int idProyeccion) throws SQLException {
        List<Map<String, Object>> comentarios = new ArrayList<>();
        try (Connection con = Conexion.getInstance().getConnect();
             PreparedStatement ps = con.prepareStatement(OBTENER_COMENTARIOS_PELICULA)) {
            ps.setInt(1, idProyeccion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> comentario = new HashMap<>();
                    comentario.put("idComentario", rs.getInt("id_comentario"));
                    comentario.put("comentario", rs.getString("comentario"));
                    comentario.put("calificacion", rs.getInt("calificacion_pelicula"));
                    comentario.put("usuarioNombre", rs.getString("usuario_nombre"));
                    comentario.put("fecha", rs.getTimestamp("fecha_comentario"));
                    comentarios.add(comentario);
                }
            }
        }
        return comentarios;
    }

    public List<Map<String, Object>> obtenerComentariosSala(int idSalaCine) throws SQLException {
        List<Map<String, Object>> comentarios = new ArrayList<>();
        try (Connection con = Conexion.getInstance().getConnect();
             PreparedStatement ps = con.prepareStatement(OBTENER_COMENTARIOS_SALA)) {
            ps.setInt(1, idSalaCine);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> comentario = new HashMap<>();
                    comentario.put("idComentario", rs.getInt("id_comentario"));
                    comentario.put("comentario", rs.getString("comentario"));
                    comentario.put("calificacion", rs.getInt("calificacion_sala"));
                    comentario.put("usuarioNombre", rs.getString("usuario_nombre"));
                    comentario.put("fecha", rs.getTimestamp("fecha_comentario"));
                    comentarios.add(comentario);
                }
            }
        }
        return comentarios;
    }

}
