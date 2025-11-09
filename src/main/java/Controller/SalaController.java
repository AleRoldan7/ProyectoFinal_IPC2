/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.Conexion;
import Dtos.Sala.NewSalaRequest;
import Dtos.Sala.SalaResponse;
import Dtos.Sala.UpdateSalaRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import ModeloEntidad.Cine.Sala;
import Services.SalaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author alejandro
 */
@Path("/sala")
public class SalaController {

    @Context
    UriInfo uriInfo;
    private SalaService salaService = new SalaService();

    @POST
    @Path("/crear-sala")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearSala(NewSalaRequest newSalaRequest) {

        try {
            Sala sala = salaService.crearSala(newSalaRequest);
            SalaResponse salaResponse = new SalaResponse(sala);
            return Response.status(201).entity(salaResponse).build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();

        }

    }

    @PUT
    @Path("/actualizar-sala")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarSala(@FormDataParam("idSala") Integer idSala,
            @FormDataParam("nombreSala") String nombreSala,
            @FormDataParam("filaAsiento") Integer filaAsiento,
            @FormDataParam("columnaAsiento") Integer columnaAsiento,
            @FormDataParam("fechaCreacion") String fechaCreacion) {

        try {
            System.out.println("ID Sala: " + idSala);
            System.out.println("Nombre: " + nombreSala);
            System.out.println("Filas: " + filaAsiento);
            System.out.println("Columnas: " + columnaAsiento);
            System.out.println("Fecha: " + fechaCreacion);

            if (idSala == null || nombreSala == null || filaAsiento == null || columnaAsiento == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Faltan datos para actualizar la sala")
                        .build();
            }

            LocalDate fecha = LocalDate.parse(fechaCreacion);

            UpdateSalaRequest updateSalaRequest = new UpdateSalaRequest();
            updateSalaRequest.setIdSala(idSala);
            updateSalaRequest.setNombreSala(nombreSala);
            updateSalaRequest.setFilaSala(filaAsiento);
            updateSalaRequest.setColumnaSala(columnaAsiento);
            updateSalaRequest.setFechaCreacion(fecha);

            salaService.actualizarSala(updateSalaRequest);

            return Response.status(Response.Status.ACCEPTED)
                    .entity(Map.of("exito", "Se actualizó la sala"))
                    .build();

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error inesperado: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{idSala}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSala(@PathParam("idSala") int idSala) {
        try {
            String sql = "SELECT s.id_sala, s.nombre_sala, s.asiento_fila, s.asiento_columna, s.fecha_creacion "
                    + "FROM sala s WHERE s.id_sala = ?";

            try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, idSala);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Map<String, Object> sala = new HashMap<>();
                    sala.put("idSala", rs.getInt("id_sala"));
                    sala.put("nombreSala", rs.getString("nombre_sala"));
                    sala.put("filaSala", rs.getInt("asiento_fila"));
                    sala.put("columnaSala", rs.getInt("asiento_columna"));
                    sala.put("fechaCreacion", rs.getDate("fecha_creacion").toString());

                    return Response.ok(sala).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("error", "Sala no encontrada"))
                            .build();
                }
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al obtener sala: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/cine/{idCine}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSalasPorCine(@PathParam("idCine") int idCine) {
        try {
            String sql = "SELECT s.id_sala, s.nombre_sala, s.asiento_fila, s.asiento_columna, s.fecha_creacion "
                    + "FROM sala s "
                    + "JOIN sala_cine sc ON s.id_sala = sc.id_sala "
                    + "WHERE sc.id_cine = ?";

            try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, idCine);
                ResultSet rs = ps.executeQuery();

                List<Map<String, Object>> salas = new ArrayList<>();

                while (rs.next()) {
                    Map<String, Object> sala = new HashMap<>();
                    sala.put("idSala", rs.getInt("id_sala"));
                    sala.put("nombreSala", rs.getString("nombre_sala"));
                    sala.put("filaSala", rs.getInt("asiento_fila"));
                    sala.put("columnaSala", rs.getInt("asiento_columna"));
                    sala.put("fechaCreacion", rs.getDate("fecha_creacion").toString());

                    salas.add(sala);
                }

                return Response.ok(salas).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al obtener salas: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodasLasSalas() {
        try {
            String sql = "SELECT id_sala, nombre_sala, asiento_fila, asiento_columna, fecha_creacion FROM sala";

            try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                List<Map<String, Object>> salas = new ArrayList<>();

                while (rs.next()) {
                    Map<String, Object> sala = new HashMap<>();
                    sala.put("idSala", rs.getInt("id_sala"));
                    sala.put("nombreSala", rs.getString("nombre_sala"));
                    sala.put("filaSala", rs.getInt("asiento_fila"));
                    sala.put("columnaSala", rs.getInt("asiento_columna"));
                    sala.put("fechaCreacion", rs.getDate("fecha_creacion").toString());

                    salas.add(sala);
                }

                return Response.ok(salas).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al obtener salas: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/admin/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSalasPorAdmin(@PathParam("idUsuario") int idUsuario) {
        try {
            String sql = "SELECT s.id_sala, s.nombre_sala, s.asiento_fila, s.asiento_columna, s.fecha_creacion, c.nombre_cine "
                    + "FROM sala s "
                    + "JOIN sala_cine sc ON s.id_sala = sc.id_sala "
                    + "JOIN cine c ON sc.id_cine = c.id_cine "
                    + "WHERE c.id_usuario = ?";

            try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, idUsuario);
                ResultSet rs = ps.executeQuery();

                List<Map<String, Object>> salas = new ArrayList<>();

                while (rs.next()) {
                    Map<String, Object> sala = new HashMap<>();
                    sala.put("idSala", rs.getInt("id_sala"));
                    sala.put("nombreSala", rs.getString("nombre_sala"));
                    sala.put("filaSala", rs.getInt("asiento_fila"));
                    sala.put("columnaSala", rs.getInt("asiento_columna"));
                    sala.put("fechaCreacion", rs.getDate("fecha_creacion").toString());
                    sala.put("nombreCine", rs.getString("nombre_cine"));

                    salas.add(sala);
                }

                return Response.ok(salas).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al obtener salas: " + e.getMessage()))
                    .build();
        }
    }
}
