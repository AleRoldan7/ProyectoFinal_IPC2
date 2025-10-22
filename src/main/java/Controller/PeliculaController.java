/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Cine.NewPeliculaRequest;
import Dtos.Cine.NewPeliculaSalaRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import Services.PeliculaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/pelicula")
public class PeliculaController {

    @Context
    UriInfo uriInfo;
    private PeliculaService peliculaService = new PeliculaService();

    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearPelicula(NewPeliculaRequest newPeliculaRequest) {

        try {
            peliculaService.crearPelicula(newPeliculaRequest);
            return Response.status(Response.Status.ACCEPTED)
                    .entity(Map.of("exito", "Pelicula creada"))
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();

        }
    }

    @POST
    @Path("/pelicula-sala")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarPeliculaSala(NewPeliculaSalaRequest newPeliculaSalaRequest) {
        try {
            peliculaService.agregarPeliculaSala(newPeliculaSalaRequest);
            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("exito", "Se agrego la pelicula"))
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (EntidadNotFound e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }
}
