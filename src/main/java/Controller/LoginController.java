/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Usuario.NewLoginRequest;
import EnumOptions.Rol;
import Excepciones.DatosInvalidos;
import ModeloEntidad.Usuario.Usuario;
import Services.LoginService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

/**
 *
 * @author alejandro
 */

@Path("/login")
public class LoginController {
    
    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuario(NewLoginRequest newLoginRequest){
        LoginService loginService = new LoginService();
        
        try {
            Usuario usuario = loginService.loginUsuario(newLoginRequest.getUserName(), newLoginRequest.getPassword(), 
                    newLoginRequest.getRol());
            
            return Response.ok(usuario).build();
            
        } catch (DatosInvalidos e) {
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (Exception e){
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el servidorrrr").build();
        }
    }
    
    
}
