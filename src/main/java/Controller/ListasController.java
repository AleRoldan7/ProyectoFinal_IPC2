/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Anuncio.AnuncioResponse;
import Dtos.Anuncio.NewAnuncioRequest;
import Dtos.Cine.CineResponse;
import Dtos.Pelicula.PeliculaResponse;
import Dtos.Sala.SalaResponse;
import Dtos.Usuario.UsuarioResponse;
import EnumOptions.Rol;
import Excepciones.EntidadNotFound;
import ModeloEntidad.Usuario.Usuario;
import Services.BoletoService;
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
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Path("/salas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSalas() {
        List<SalaResponse> salaLista = listaService.obtenerSala()
                .stream()
                .map(SalaResponse::new)
                .toList();

        System.out.println("Salas encontradas: " + salaLista.size());
        return Response.ok(salaLista).build();
    }

    @GET
    @Path("/salas/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSalas(@PathParam("idUsuario") Integer idUsuario) {
        System.out.println("ID usuario recibido: " + idUsuario);
        List<SalaResponse> salaLista = listaService.obtenerSalaAdmin(idUsuario)
                .stream()
                .map(SalaResponse::new)
                .toList();

        System.out.println("Salas encontradas: " + salaLista.size());
        return Response.ok(salaLista).build();
    }

    @GET
    @Path("/anuncios/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAnuncios(@PathParam("idUsuario") Integer idUsuario) {

        try {
            List<NewAnuncioRequest> anuncios = listaService.obtenerAnuncios(idUsuario);
            return Response.ok(anuncios).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }

    }

    @GET
    @Path("/imagen/{idAnuncio}")
    @Produces("image/jpeg")
    public Response obtenerImagen(@PathParam("idAnuncio") Integer idAnuncio) {
        try {
            InputStream imagenStream = listaService.obtenerImagen(idAnuncio);
            if (imagenStream == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(imagenStream).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/cine")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCine() {

        System.out.println("SI jalaaa");
        List<CineResponse> listaCines = listaService.obtenerCines()
                .stream()
                .map(CineResponse::new)
                .toList();

        return Response.ok(listaCines).build();

    }

    @GET
    @Path("/salas/cine/{idCine}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSalaCine(@PathParam("idCine") Integer idCine) {

        try {

            List<SalaResponse> salaCine = listaService.obtenerSalaCine(idCine)
                    .stream()
                    .map(SalaResponse::new)
                    .toList();

            if (salaCine.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("mensaje", "No se encontraron salas para el cine con ID " + idCine))
                        .build();
            }

            return Response.ok(salaCine).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/peliculas/sala/{idSala}/cine/{idCine}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPeliculasPorSalaYCine(
            @PathParam("idSala") Integer idSala,
            @PathParam("idCine") Integer idCine) {

        try {
            List<Map<String, Object>> peliculas = listaService.obtenerPeliculasPorSalaYCine(idSala, idCine);

            if (peliculas.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("mensaje", "No se encontraron películas para la sala " + idSala + " y cine " + idCine))
                        .build();
            }

            return Response.ok(peliculas).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/lista/anuncios/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnunciosUsuario(@PathParam("idUsuario") int idUsuario) {
        try {
            List<AnuncioResponse> anuncios = listaService.getAnuncioPorUsuario(idUsuario);
            return Response.ok(anuncios).build();
        } catch (Exception e) {
            return Response.status(500).entity("Error al obtener anuncios").build();
        }
    }
}
