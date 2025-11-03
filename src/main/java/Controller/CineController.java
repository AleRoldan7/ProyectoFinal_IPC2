/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Cine.NewCineRequest;
import Dtos.Cine.UpdateCineRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import Services.CineService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.time.LocalDate;
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
            @PathParam("costoCine") double costoCine) throws  EntidadNotFound {

        try {

            if (idCine == null || idUsuario ==  null) {
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

}
