/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Pelicula.NewPeliculaRequest;
import Dtos.Cine.NewPeliculaSalaRequest;
import Dtos.Pelicula.UpdatePeliculaRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import Services.PeliculaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.Map;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearPelicula(@FormDataParam("tituloPelicula") String tituloPelicula,
            @FormDataParam("sinopsisPelicula") String sinopsisPelicula,
            @FormDataParam("duracionPelicula") String duracionPelicula,
            @FormDataParam("castPelicula") String castPelicula,
            @FormDataParam("directorPelicula") String directorPelicula,
            @FormDataParam("posterPelicula") InputStream posterPelicula,
            @FormDataParam("posterPelicula") FormDataContentDisposition fileDetail) throws IOException {

        try {

            if (tituloPelicula == null || sinopsisPelicula == null
                    || duracionPelicula == null || castPelicula == null || directorPelicula == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Error en los datos enviados")
                        .build();
            }

            LocalTime duracion = LocalTime.parse(duracionPelicula);

            NewPeliculaRequest newPeliculaRequest = new NewPeliculaRequest();
            newPeliculaRequest.setTituloPelicula(tituloPelicula);
            newPeliculaRequest.setSinopsisPelicula(sinopsisPelicula);
            newPeliculaRequest.setDuracionPelicula(duracion);
            newPeliculaRequest.setCastPelicula(castPelicula);
            newPeliculaRequest.setDirectorPelicula(directorPelicula);
            newPeliculaRequest.setPosterPelicula(posterPelicula);

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

    @POST
    @Path("/actualizar-pelicula")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarPelicula(@FormDataParam("idPelicula") Integer idPelicula,
            @FormDataParam("tituloPelicula") String tituloPelicula,
            @FormDataParam("sinopsisPelicula") String sinopsisPelicula,
            @FormDataParam("duracionPelicula") String duracionPelicula,
            @FormDataParam("castPelicula") String castPelicula,
            @FormDataParam("directorPelicula") String directorPelicula,
            @FormDataParam("posterPelicula") InputStream posterPelicula,
            @FormDataParam("posterPelicula") FormDataContentDisposition fileDetail) throws IOException, EntidadNotFound {

        try {

            if (idPelicula == null || tituloPelicula == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Faltan datos para actualizar la pelicula")
                        .build();
            }

            LocalTime duracion = LocalTime.parse(duracionPelicula);
            
            UpdatePeliculaRequest updatePeliculaRequest = new UpdatePeliculaRequest();
            updatePeliculaRequest.setIdPelicula(idPelicula);
            updatePeliculaRequest.setTituloPelicula(tituloPelicula);
            updatePeliculaRequest.setSinopsisPelicula(sinopsisPelicula);
            updatePeliculaRequest.setDuracionPelicula(duracion);
            updatePeliculaRequest.setCastPelicula(castPelicula);
            updatePeliculaRequest.setDirectorPelicula(directorPelicula);
            updatePeliculaRequest.setPosterPelicula(posterPelicula);

            peliculaService.actualizarPelicula(updatePeliculaRequest);

            return Response.status(Response.Status.ACCEPTED)
                    .entity(Map.of("exito", "Se actualizo la pelicula"))
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
