/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Proyeccion.NewProyeccionRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Services.ProyeccionService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/proyectar")
public class ProyectarController {
    
    @Context
    UriInfo uriInfo;
    private ProyeccionService proyeccion = new ProyeccionService();
    
    @POST
    @Path("/asignar-pelicula")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(NewProyeccionRequest req) throws EntidadNotFound {
       
        try {
            proyeccion.asignarProyeccion(req, req.getIdUsuario());
            return Response.status(201)
                    .entity(Map.of("exito", "Proyección creada correctamente"))
                    .build();
        } catch (DatosInvalidos e) {
            return Response.status(400).entity(Map.of("error", e.getMessage())).build();
        }
    }
}
