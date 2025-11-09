/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.CineDBA;
import ConexionDBA.Conexion;
import Dtos.Cine.NewCineRequest;
import Dtos.Cine.UpdateCineRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import Services.CineService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/cine")
public class CineController {

    @Context
    UriInfo uriInfo;
    private CineService cineService = new CineService();
    private CineDBA cineDBA = new CineDBA();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearCine(NewCineRequest newCineRequest) {

        try {
            cineService.crearCine(newCineRequest);
            return Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Cine creado\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();

        }
    }

    @POST
    @Path("/actualizar-cine")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarCine(@PathParam("idCine") Integer idCine,
            @PathParam("idUsuario") Integer idUsuario,
            @PathParam("fechaCreacion") String fechaCreacion,
            @PathParam("costoCine") double costoCine) throws EntidadNotFound {

        try {

            if (idCine == null || idUsuario == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Faltan datos para actualizar el cine")
                        .build();
            }

            LocalDate fecha = LocalDate.parse(fechaCreacion);

            UpdateCineRequest updateCineRequest = new UpdateCineRequest();
            updateCineRequest.setIdCine(idCine);
            updateCineRequest.setIdUsuario(idUsuario);
            updateCineRequest.setFechaCreacion(fecha);
            updateCineRequest.setCostoCine(costoCine);

            cineService.actualizarCine(updateCineRequest);

            return Response.status(Response.Status.ACCEPTED)
                    .entity(Map.of("exito", "Se actualizo el cine"))
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error inesperado: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/{idCine}/recargar-cartera")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recargar(@PathParam("idCine") int idCine, Map<String, Double> body, @HeaderParam("idUsuario") int idUsuario) throws EntidadNotFound {
        try {
            double monto = body.get("monto");
            cineService.recargarCarteraDelCine(idCine, monto, idUsuario);
            return Response.ok(Map.of("mensaje", "Cartera recargada")).build();
        } catch (DatosInvalidos e) {
            return Response.status(400).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @POST
    @Path("/{idCine}/bloquear-anuncios")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bloquear(@PathParam("idCine") int idCine, Map<String, Integer> body, @HeaderParam("idUsuario") int idUsuario) throws EntidadNotFound {
        try {
            int dias = body.get("dias");
            cineService.bloquearAnuncios(idCine, dias, idUsuario);
            return Response.ok(Map.of("mensaje", "Anuncios bloqueados por " + dias + " días")).build();
        } catch (DatosInvalidos e) {
            return Response.status(400).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @PUT
    @Path("/costo-bloqueo/{idCine}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setCosto(@PathParam("idCine") int idCine, Map<String, Double> body) {
        double costo = body.get("costo");
        String sql = "INSERT INTO costo_bloqueo_anuncio (id_cine, costo_por_dia) VALUES (?, ?) ON DUPLICATE KEY UPDATE costo_por_dia = ?";
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCine);
            ps.setDouble(2, costo);
            ps.setDouble(3, costo);
            ps.executeUpdate();
            return Response.ok(Map.of("mensaje", "Costo actualizado")).build();
        } catch (SQLException e) {
            return Response.status(500).entity(Map.of("error", "Error DB")).build();
        }
    }

    @GET
    @Path("/{idCine}/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerInfoCine(@PathParam("idCine") int idCine) {
        try {
            Map<String, Object> info = new HashMap<>();
            info.put("dinero_cartera", cineDBA.obtenerSaldoCartera(idCine));
            info.put("costo_por_dia", cineDBA.obtenerCostoBloqueo(idCine));
            info.put("bloqueo_activo", cineDBA.tieneBloqueoActivo(idCine));

            return Response.ok(info).build();
        } catch (Exception e) {
            return Response.status(500).entity(Map.of("error", e.getMessage())).build();
        }
    }
}
