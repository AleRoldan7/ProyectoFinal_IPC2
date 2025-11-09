/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ComentarioDBA;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
public class ComentarioService {

    private final ComentarioDBA comentarioDBA = new ComentarioDBA();

    public void comentarPelicula(int idUsuario, int idProyeccion, String comentario, int calificacion) throws SQLException {
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("Calificación debe estar entre 1 y 5");
        }
        comentarioDBA.agregarComentarioPelicula(idUsuario, idProyeccion, comentario, calificacion);
    }

    public void comentarSala(int idUsuario, int idSalaCine, String comentario, int calificacion) throws SQLException {
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("Calificación debe estar entre 1 y 5");
        }
        comentarioDBA.agregarComentarioSala(idUsuario, idSalaCine, comentario, calificacion);
    }

    public void bloquearComentariosSala(int idSalaCine, boolean bloquear) throws SQLException {
        comentarioDBA.bloquearComentariosSala(idSalaCine, bloquear);
    }

    public void bloquearVisibilidadSala(int idSalaCine, boolean bloquear) throws SQLException {
        comentarioDBA.bloquearVisibilidadSala(idSalaCine, bloquear);
    }

    public List<Map<String, Object>> obtenerComentariosPelicula(int idProyeccion) throws SQLException {
        return comentarioDBA.obtenerComentariosPelicula(idProyeccion);
    }

    public List<Map<String, Object>> obtenerComentariosSala(int idSalaCine) throws SQLException {
        return comentarioDBA.obtenerComentariosSala(idSalaCine);
    }
}
