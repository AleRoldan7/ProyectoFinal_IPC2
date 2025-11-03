/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.BoletoDBA;
import Dtos.Boleto.NewBoletoRequest;
import Dtos.Boleto.UpdateBoletoRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import Excepciones.SinDinero;
import Services.BoletoService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

/**
 *
 * @author alejandro
 */
@Path("/boletos")
public class BoletoController {

    @Context
    UriInfo uriInfo;
    private BoletoService boletoService = new BoletoService();

    @POST
    @Path("/compra-boleto")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearSala(NewBoletoRequest newBoletoRequest) throws SinDinero {

        try {
            boletoService.comprarBoleto(newBoletoRequest);
            return Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Boleto Comprado\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (EntityExists e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();

        }

    }

    @GET
    @Path("/asientos-ocupados/{idPeliculaSala}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAsientosOcupados(@PathParam("idPeliculaSala") int idPeliculaSala) {
        BoletoDBA db = new BoletoDBA();
        try {
            return Response.ok(db.obtenerAsientosOcupados(idPeliculaSala)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error al obtener asientos ocupados\"}")
                    .build();
        }
    }

    @GET
    @Path("/peliculas/{idCine}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPeliculasPorCine(@PathParam("idCine") int idCine) throws SQLException {
        List<Map<String, Object>> peliculas = boletoService.obtenerPeliculasPorCine(idCine);
        return Response.ok(peliculas).build();
    }
    
    @POST
    @Path("/actualizar-boleto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarPelicula(@PathParam("idBoleto") Integer idBoleto,
            @PathParam("idPeliculaSala") Integer idPeliculaSala,
            @PathParam("filaAsiento") Integer filaAsiento,
            @PathParam("columnaAsiento") Integer columnaAsiento) throws  EntidadNotFound {

        try {

            if (idBoleto == null || idPeliculaSala ==  null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Faltan datos para actualizar el boleto")
                        .build();
            }
            
            UpdateBoletoRequest updateBoletoRequest = new UpdateBoletoRequest();
            updateBoletoRequest.setIdBoleto(idBoleto);
            updateBoletoRequest.setIdPeliculaSala(idPeliculaSala);
            updateBoletoRequest.setFilaAsiento(filaAsiento);
            updateBoletoRequest.setColumnaAsiento(columnaAsiento);
            
            boletoService.actualizarBoleto(updateBoletoRequest);

            return Response.status(Response.Status.ACCEPTED)
                    .entity(Map.of("exito", "Se actualizo el boleto"))
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
