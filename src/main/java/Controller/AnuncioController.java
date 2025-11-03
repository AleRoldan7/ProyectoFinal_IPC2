/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dtos.Anuncio.NewAnuncioRequest;
import EnumOptions.TipoAnuncio;
import Excepciones.DatosInvalidos;
import Services.AnuncioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;
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
    public Response crearAnuncio(@FormDataParam("tipoAnuncio") TipoAnuncio tipoAnuncio,
            @FormDataParam("mensajeAnuncio") String mensajeAnuncio,
            @FormDataParam("nombreAnunciante") Integer nombreAnunciante,
            @FormDataParam("fechaInicio") String fechaInicio,
            @FormDataParam("diasVigente") Integer diasVigente,
            @FormDataParam("imagenAnuncio") InputStream imagenAnuncio,
            @FormDataParam("videoAnuncio") String videoAnuncio) {

        try {

            if (fechaInicio == null || fechaInicio.isEmpty()) {
                throw new DatosInvalidos("La fecha de inicio no puede ser nula");
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

            Map<String, Object> responseMap = Map.of(
                    "tipoAnuncio", tipoAnuncio.name(),
                    "mensajeAnuncio", mensajeAnuncio,
                    "nombreAnunciante", nombreAnunciante,
                    "fechaInicio", fechaInicioParse.toString(),
                    "diasVigente", diasVigente,
                    "precio", newAnuncioRequest.getPrecio()
            );

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of(responseMap, "Se creo el anuncio"))
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

    
}