/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.BoletoDBA;
import Dtos.Boleto.NewBoletoRequest;
import Dtos.Boleto.UpdateBoletoRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import Excepciones.SinDinero;
import Services.BoletoService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 *
 * @author alejandro
 */
@Path("/boletos")
public class BoletoController {

    @Context
    UriInfo uriInfo;
    private BoletoService boletoService = new BoletoService();
    private BoletoDBA boletoDBA = new BoletoDBA();

    @POST
    @Path("/compra-boleto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response comprarBoleto(NewBoletoRequest newBoletoRequest) {
        try {
            boletoService.comprarBoleto(newBoletoRequest);
            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensaje", "Boleto comprado exitosamente"))
                    .build();
        } catch (DatosInvalidos | EntityExists e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (SinDinero e) {
            return Response.status(Response.Status.PAYMENT_REQUIRED)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error inesperado: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/asientos-ocupados/{idProyeccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAsientosOcupados(@PathParam("idProyeccion") int idProyeccion) {
        try {
            List<Map<String, Integer>> asientos = boletoDBA.obtenerAsientosOcupados(idProyeccion);
            return Response.ok(asientos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al obtener asientos ocupados"))
                    .build();
        }
    }

    @GET
    @Path("/cartelera/global")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCarteleraGlobal(
            @QueryParam("buscar") String buscar
    ) {
        try {
            List<Map<String, Object>> cartelera = boletoService.obtenerPeliculasPorCine(buscar);
            if (cartelera.isEmpty()) {
                return Response.ok(new ArrayList<>()).build();
            }
            return Response.ok(cartelera).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity(Map.of("error", "Error al cargar cartelera global"))
                    .build();
        }
    }

    @PUT
    @Path("/actualizar-boleto/{idBoleto}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarBoleto(@PathParam("idBoleto") Integer idBoleto, UpdateBoletoRequest request) {
        try {

            request.setIdBoleto(idBoleto);
            boletoService.actualizarBoleto(request);
            return Response.ok(Map.of("exito", "Boleto actualizado correctamente")).build();
        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error inesperado: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/peliculas-compradas/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPeliculasCompradas(@PathParam("idUsuario") int idUsuario) {
        List<Map<String, Object>> peliculas = boletoService.obtenerPeliculasCompradas(idUsuario);
        return Response.ok(peliculas).build();
    }
}
