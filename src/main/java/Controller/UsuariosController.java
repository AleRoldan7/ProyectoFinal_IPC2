/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Usuario.NewUsuarioRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import Services.UsuarioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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

   
}
