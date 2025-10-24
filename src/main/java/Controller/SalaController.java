/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Cine.NewSalaRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import Services.SalaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

/**
 *
 * @author alejandro
 */
@Path("/sala")
public class SalaController {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearSala(NewSalaRequest newSalaRequest) {
        SalaService salaService = new SalaService();

        try {
            salaService.crearSala(newSalaRequest);
            return Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Sala creada\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();

        }

    }
}
