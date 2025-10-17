/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.UsuarioDBA;
import Dtos.Usuario.NewUsuarioRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import Services.UsuarioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/users")
public class UsuariosController {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearUsuario(NewUsuarioRequest newUsuarioRequest) {
        UsuarioService usuarioService = new UsuarioService();

        try {

            usuarioService.crearUsuario(newUsuarioRequest);

            return Response.status(Response.Status.CREATED).build();
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Path("/{userName}/{rolUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response recargarCartera(@PathParam("userName") String userName,
            @PathParam("rolUsuario") String rol,
            @FormParam("cantidadRecarga") double cantidadRecarga) {

        UsuarioDBA usuarioDBA = new UsuarioDBA();

        if (cantidadRecarga <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "La cantidad de recarga debe ser mayor a 0."))
                    .build();
        }

        try {
            usuarioDBA.recargarCartera(userName, cantidadRecarga);
            return Response.ok(Map.of("message", "Cartera recargada exitosamente")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error inesperado: " + e.getMessage()))
                    .build();
        }
    }

}
