/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.AnuncioDBA;
import Dtos.Anuncio.AnuncioResponse;
import Dtos.Anuncio.NewAnuncioRequest;
import EnumOptions.TipoAnuncio;
import Excepciones.DatosInvalidos;
import Services.AnuncioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author alejandro
 */
@Path("/anuncio")
public class AnuncioController {

    private AnuncioService anuncioService = new AnuncioService();

    @POST
    @Path("/crear-anuncio")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearAnuncio(@FormDataParam("tipoAnuncio") TipoAnuncio tipoAnuncio,
            @FormDataParam("mensajeAnuncio") String mensajeAnuncio,
            @FormDataParam("nombreAnunciante") Integer nombreAnunciante,
            @FormDataParam("fechaInicio") String fechaInicio,
            @FormDataParam("diasVigente") Integer diasVigente,
            @FormDataParam("imagenAnuncio") InputStream imagenAnuncio,
            @FormDataParam("imagenAnuncio") FormDataContentDisposition imagenFile,
            @FormDataParam("videoAnuncio") String videoAnuncio) {

        try {

            if (fechaInicio == null || fechaInicio.isEmpty()) {
                throw new DatosInvalidos("La fecha de inicio no puede ser nula");
            }

            if (tipoAnuncio == TipoAnuncio.TEXTO_IMAGEN) {
                if (imagenAnuncio == null || imagenFile == null || imagenFile.getFileName() == null || imagenFile.getFileName().isEmpty()) {
                    throw new DatosInvalidos("Debe subir una imagen para anuncios con imagen");
                }
                if (!imagenFile.getFileName().toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$")) {
                    throw new DatosInvalidos("Solo se permiten imágenes: JPG, PNG, GIF");
                }
            }

            LocalDate fechaInicioParse = LocalDate.parse(fechaInicio);

            NewAnuncioRequest newAnuncioRequest = new NewAnuncioRequest();
            newAnuncioRequest.setTipoAnuncio(tipoAnuncio);
            newAnuncioRequest.setMensajeAnuncio(mensajeAnuncio);
            newAnuncioRequest.setNombreAnunciante(nombreAnunciante);
            newAnuncioRequest.setFechaInicio(fechaInicioParse);
            newAnuncioRequest.setDiasVigencia(diasVigente);
            newAnuncioRequest.setImagenAnuncio(imagenAnuncio);
            newAnuncioRequest.setVideoAnuncio(videoAnuncio);

            anuncioService.crearAnuncio(newAnuncioRequest);

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensaje", "Anuncio creado exitosamente"))
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al crear el anuncio: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/imagen/{idAnuncio}")
    @Produces({"image/jpeg", "image/png", "image/gif"})
    public Response getImagen(@PathParam("idAnuncio") int idAnuncio) {
        try {
            byte[] imagen = anuncioService.getImagenAnuncio(idAnuncio);
            if (imagen == null || imagen.length == 0) {
                return Response.status(404)
                        .entity("Imagen no encontrada para id: " + idAnuncio)
                        .build();
            }

            String contentType = detectarContentType(imagen);
            return Response.ok(imagen)
                    .header("Content-Type", contentType)
                    .header("Access-Control-Allow-Origin", "*") // CORS
                    .header("Cache-Control", "public, max-age=31536000")
                    .build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("Error interno: " + e.getMessage())
                    .build();
        }
    }

    private String detectarContentType(byte[] bytes) {
        if (bytes.length < 4) {
            return "image/jpeg";
        }
        String header = String.format("%02x%02x%02x%02x", bytes[0], bytes[1], bytes[2], bytes[3]).toLowerCase();
        if (header.startsWith("ffd8ffe")) {
            return "image/jpeg";
        }
        if (header.startsWith("89504e47")) {
            return "image/png";
        }
        if (header.startsWith("47494638")) {
            return "image/gif";
        }
        return "image/jpeg";
    }

    @GET
    @Path("/lista/anuncios/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnunciosUsuario(@PathParam("idUsuario") int idUsuario) {
        try {
            AnuncioDBA anuncioDBA = new AnuncioDBA();

            List<Map<String, Object>> anuncios = anuncioDBA.getAnunciosPorUsuario(idUsuario);
            return Response.ok(anuncios).build();
        } catch (Exception e) {
            return Response.status(500).entity("Error al obtener anuncios").build();
        }
    }

    @GET
    @Path("/activos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnunciosActivos() {
        try {
            List<AnuncioResponse> anuncios = anuncioService.obtenerAnunciosActivos();
            return Response.ok(anuncios).build();
        } catch (Exception e) {
            return Response.status(500).entity("Error al obtener anuncios activos").build();
        }
    }

    @GET
    @Path("/visibles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnunciosVisibles() {
        return Response.ok(anuncioService.obtenerAnunciosVisibles()).build();
    }

    @PUT
    @Path("/toggle/{idAnuncio}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response toggleAnuncio(@PathParam("idAnuncio") int idAnuncio,
            @QueryParam("activo") boolean activo,
            @HeaderParam("idUsuario") int idUsuario) {
        try {
            anuncioService.toggleAnuncio(idAnuncio, activo, idUsuario);
            return Response.ok(Map.of("mensaje", "Anuncio actualizado")).build();
        } catch (DatosInvalidos e) {
            return Response.status(403).entity(Map.of("error", e.getMessage())).build();
        }
    }

}
