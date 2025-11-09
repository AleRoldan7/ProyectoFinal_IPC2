/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Anuncio.Bloqueo.BloqueoAnuncioResponse;
import Dtos.Anuncio.Bloqueo.NewBloqueoAnuncioRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Services.BloqueoAnuncioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/bloqueo-anuncios")
public class BloqueoAnuncioController {
    
    private BloqueoAnuncioService bloqueoService = new BloqueoAnuncioService();

    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearBloqueo(NewBloqueoAnuncioRequest request) {
        try {
            BloqueoAnuncioResponse response = bloqueoService.crearBloqueoAnuncios(request);
            return Response.status(Response.Status.CREATED)
                    .entity(response)
                    .build();
                    
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error en la base de datos: " + e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/actualizar-costo/{idCine}/{nuevoCosto}/{idAdmin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarCostoBloqueo(
            @PathParam("idCine") Integer idCine,
            @PathParam("nuevoCosto") Double nuevoCosto,
            @PathParam("idAdmin") Integer idAdmin) {
        
        try {
            bloqueoService.actualizarCostoBloqueo(idCine, nuevoCosto, idAdmin);
            return Response.status(Response.Status.OK)
                    .entity(Map.of("mensaje", "Costo de bloqueo actualizado exitosamente"))
                    .build();
                    
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error en la base de datos: " + e.getMessage()))
                    .build();
        }
    }
}
