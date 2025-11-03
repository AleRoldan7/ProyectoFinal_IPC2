/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Sala.NewSalaRequest;
import Dtos.Sala.UpdateSalaRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import Services.SalaService;
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
@Path("/sala")
public class SalaController {

    @Context
    UriInfo uriInfo;
    private SalaService salaService = new SalaService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearSala(NewSalaRequest newSalaRequest) {

        try {
            salaService.crearSala(newSalaRequest);
            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensaje", "Cine creado correctamente"))
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();

        }

    }

    @POST
    @Path("/actualizar-sala")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarSala(@PathParam("idSala") Integer idSala,
            @PathParam("nombreSala") String nombreSala,
            @PathParam("filaAsiento") int filaAsiento,
            @PathParam("columnaAsiento") int columnaAsiento,
            @PathParam("fechaCreacion") String fechaCreacion) throws EntidadNotFound {

        try {

            if (idSala == null || nombreSala == null) {
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
                    .entity(Map.of("exito", "Se actualizo la sala"))
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
