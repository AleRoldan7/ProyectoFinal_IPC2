/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Cine.PeliculaResponse;
import Dtos.Cine.SalaResponse;
import Dtos.Usuario.UsuarioResponse;
import EnumOptions.Rol;
import Excepciones.EntidadNotFound;
import ModeloEntidad.Usuario.Usuario;
import Services.ListaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;

/**
 *
 * @author alejandro
 */
@Path("/lista")
public class ListasController {

    @Context
    UriInfo uriInfo;
    private ListaService listaService = new ListaService();

    @GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarios() {
        System.out.println("SI esta aca");
        List<UsuarioResponse> usuarios = listaService.obtnerDatosUsuario()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
        System.out.println("Usuarios" + usuarios.size());
        return Response.ok(usuarios).build();
    }

    @GET
    @Path("/adminCine")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAdminCine() {
        System.out.println("Entro a los admin de cines");
        List<UsuarioResponse> usuarioCine = listaService.obtenerAdminCine()
                .stream()
                .map(UsuarioResponse::new)
                .toList();

        return Response.ok(usuarioCine).build();

    }

    @GET
    @Path("/peliculas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPeliculas() {
        System.out.println("SI jalaaa");
        List<PeliculaResponse> peliculaLista = listaService.obtenerPeliculas()
                .stream()
                .map(PeliculaResponse::new)
                .toList();

        return Response.ok(peliculaLista).build();
    }

    @GET
    @Path("/salas/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSalas(@PathParam("idUsuario") Integer idUsuario) {
        System.out.println("ID usuario recibido: " + idUsuario);
        List<SalaResponse> salaLista = listaService.obtenerSala(idUsuario)
                .stream()
                .map(SalaResponse::new)
                .toList();

        System.out.println("Salas encontradas: " + salaLista.size());
        return Response.ok(salaLista).build();
    }

}
