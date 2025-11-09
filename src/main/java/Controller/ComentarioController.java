/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Services.ComentarioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService = new ComentarioService();

    @POST
    @Path("/pelicula")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response comentarPelicula(Map<String, Object> comentarioData) {
        try {
            int idUsuario = (Integer) comentarioData.get("idUsuario");
            int idProyeccion = (Integer) comentarioData.get("idProyeccion");
            String comentario = (String) comentarioData.get("comentario");
            int calificacion = (Integer) comentarioData.get("calificacion");

            comentarioService.comentarPelicula(idUsuario, idProyeccion, comentario, calificacion);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(Map.of("error", e.getMessage())).build();
        } catch (SQLException e) {
            return Response.status(500).entity(Map.of("error", "Error en base de datos")).build();
        }
    }

    @POST
    @Path("/sala")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response comentarSala(Map<String, Object> comentarioData) {
        try {
            int idUsuario = (Integer) comentarioData.get("idUsuario");

            // Acepta idSala o idSalaCine
            Integer idSalaCineValue = (Integer) comentarioData.get("idSalaCine");
            if (idSalaCineValue == null) {
                idSalaCineValue = (Integer) comentarioData.get("idSala");
            }
            if (idSalaCineValue == null) {
                return Response.status(400).entity(Map.of("error", "Falta idSalaCine o idSala")).build();
            }

            int idSalaCine = idSalaCineValue;

            String comentario = (String) comentarioData.get("comentario");
            int calificacion = (Integer) comentarioData.get("calificacion");

            comentarioService.comentarSala(idUsuario, idSalaCine, comentario, calificacion);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(Map.of("error", e.getMessage())).build();
        } catch (SQLException e) {
            return Response.status(500).entity(Map.of("error", "Error en base de datos")).build();
        }
    }

    @PUT
    @Path("/sala/comentarios/{idSalaCine}")
    public Response bloquearComentarios(@PathParam("idSalaCine") int idSalaCine, @QueryParam("bloquear") boolean bloquear) {
        try {
            comentarioService.bloquearComentariosSala(idSalaCine, bloquear);
            return Response.ok(Map.of("mensaje", bloquear ? "Comentarios bloqueados" : "Comentarios desbloqueados")).build();
        } catch (SQLException e) {
            return Response.status(500).entity(Map.of("error", "Error al actualizar sala")).build();
        }
    }

    @PUT
    @Path("/sala/visibilidad/{idSalaCine}")
    public Response bloquearVisibilidad(@PathParam("idSalaCine") int idSalaCine, @QueryParam("bloquear") boolean bloquear) {
        try {
            comentarioService.bloquearVisibilidadSala(idSalaCine, bloquear);
            return Response.ok(Map.of("mensaje", bloquear ? "Sala oculta" : "Sala visible")).build();
        } catch (SQLException e) {
            return Response.status(500).entity(Map.of("error", "Error al actualizar visibilidad")).build();
        }
    }

    @GET
    @Path("/pelicula/{idProyeccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentariosPelicula(@PathParam("idProyeccion") int idProyeccion) {
        try {
            List<Map<String, Object>> comentarios = comentarioService.obtenerComentariosPelicula(idProyeccion);
            return Response.ok(comentarios).build();
        } catch (SQLException e) {
            return Response.status(500).entity(Map.of("error", "Error al cargar comentarios")).build();
        }
    }

    @GET
    @Path("/sala/{idSalaCine}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentariosSala(@PathParam("idSalaCine") int idSalaCine) {
        try {
            List<Map<String, Object>> comentarios = comentarioService.obtenerComentariosSala(idSalaCine);
            return Response.ok(comentarios).build();
        } catch (SQLException e) {
            return Response.status(500).entity(Map.of("error", "Error al cargar comentarios")).build();
        }
    }

}
