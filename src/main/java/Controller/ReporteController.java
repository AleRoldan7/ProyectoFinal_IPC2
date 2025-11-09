/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ConexionDBA.ReportesDBA;
import Dtos.Reportes.Boleto.ReporteBoletoRequest;
import Dtos.Reportes.Comentarios.NewReporteComentarioRequest;
import Dtos.Reportes.Comentarios.ReporteComentarioSalaResponse;
import Dtos.Reportes.Pelicula.NewReportePeliculaRequest;
import Dtos.Reportes.Pelicula.ReportePeliculaResponse;
import Dtos.Reportes.Sala.NewReporteSalaRequest;
import Dtos.Reportes.Sala.ReporteSalaResponse;
import Dtos.Reportes.Sistema.NewReporteAnuncioRequest;
import Dtos.Reportes.Sistema.NewReporteGananciaRequest;
import Dtos.Reportes.Sistema.ReporteAnunciosResponse;
import Dtos.Reportes.Sistema.ReporteGananciaAnunciante;
import Dtos.Reportes.Sistema.ReporteGananciaAnuncianteRequest;
import Dtos.Reportes.Sistema.ReporteGananciaResponse;
import Dtos.Reportes.Sistema.ReporteSalaComentadaRequest;
import Dtos.Reportes.Sistema.ReporteSalaComentadaResponse;
import Dtos.Reportes.Sistema.ReporteSalaPopularResponse;
import Dtos.Reportes.Sistema.SalaPopularRequest;
import Dtos.Sala.SalaResponse;
import Excepciones.DatosInvalidos;
import Services.ReporteService;
import jakarta.ws.rs.BeanParam;
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
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
@Path("/reportes")
public class ReporteController {

    @Context
    UriInfo uriInfo;
    private ReporteService reporteService = new ReporteService();
    private ReportesDBA reporteDBA = new ReportesDBA();

    @POST
    @Path("/boletos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReporteBoletos(ReporteBoletoRequest reporteBoletoRequest) {

        try {
            return Response.ok(reporteService.obtenerReporteBoletos(reporteBoletoRequest)).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al generar reporte: " + e.getMessage()).build();
        }

    }

    @POST
    @Path("/boletos/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/pdf")
    public Response descargarPDF(ReporteBoletoRequest request) {
        try {
            byte[] pdf = reporteService.generarPDF(request);
            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=Reporte_Boletos.pdf")
                    .build();
        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    /*
    @POST
    @Path("/comentarios")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentarios(NewReporteComentarioRequest request) {

        try {
            return Response.ok(reporteService.obtenerComentario(request)).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener comentarios: " + e.getMessage()).build();
        }
    }
     */
 /*
    @POST
    @Path("/comentarios/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/pdf")
    public Response pdfComentarios(NewReporteComentarioRequest req) {
        try {
            byte[] pdf = reporteService.generarPDFComentarios(req);
            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=comentarios.pdf")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Error al generar PDF: " + e.getMessage()).build();
        }
    }
     */
    @POST
    @Path("/comentarios")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentariosSalas(NewReporteComentarioRequest req) {
        List<ReporteComentarioSalaResponse> data = reporteDBA.obtenerComentariosSalas(req);
        return Response.ok(data).build();
    }

    @POST
    @Path("/comentarios/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/pdf")
    public Response pdfComentariosSalas(NewReporteComentarioRequest req) {
        try {
            byte[] pdf = reporteService.generarPDFComentariosSalas(req);
            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=reporte_comentarios_salas.pdf")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/peliculas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPeliculasPorSala(NewReportePeliculaRequest req) {
        List<ReportePeliculaResponse> data = reporteDBA.obtenerPeliculasPorSala(req);
        return Response.ok(data).build();
    }

    @POST
    @Path("/peliculas/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/pdf")
    public Response pdfPeliculas(NewReportePeliculaRequest req) {
        try {
            byte[] pdf = reporteService.generarPDFPeliculas(req);
            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=reporte_peliculas_proyectadas.pdf")
                    .build();
        } catch (Exception e) {
            return Response.status(500).entity("Error al generar PDF: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/salas/{idCine}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSalas(@PathParam("idCine") int idCine) {
        List<SalaResponse> salas = reporteDBA.obtenerSalasPorCine(idCine);
        return Response.ok(salas).build();
    }

    @POST
    @Path("/salas-gustadas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSalasGustadas(NewReporteSalaRequest newReporteSalaRequest) {
        List<ReporteSalaResponse> data = reporteDBA.obtenerSalasGustadas(newReporteSalaRequest);
        return Response.ok(data).build();
    }

    @POST
    @Path("/salas-gustadas/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/pdf")
    public Response pdfSalasGustadas(NewReporteSalaRequest newReporteSalaRequest) {
        try {
            byte[] pdf = reporteService.generarPDFSalasGustadas(newReporteSalaRequest);
            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=top_salas_gustadas.pdf")
                    .build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al generar PDF: " + e.getMessage());
            return Response.status(500)
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    @Path("/ganancias")
    public Response generarReporteGanancias(NewReporteGananciaRequest request) {
        try {
            List<ReporteGananciaResponse> resultado = reporteService.generarReporteGanancias(request);
            return Response.ok(resultado).build();
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al generar reporte de ganancias: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/anuncios")
    public Response generarReporteAnuncios(NewReporteAnuncioRequest request) {
        try {
            List<ReporteAnunciosResponse> resultado = reporteService.generarReporteAnuncios(request);
            return Response.ok(resultado).build();
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al generar reporte de anuncios: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/anuncios/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/pdf")
    public Response pdfAnuncios(NewReporteAnuncioRequest request) {
        try {
            System.out.println("=== INICIANDO PDF ANUNCIOS ===");
            System.out.println("Request recibido - Fecha inicio: " + request.getFechaInicio() + ", Fecha fin: " + request.getFechaFin());

            List<ReporteAnunciosResponse> datosPrueba = reporteService.generarReporteAnuncios(request);
            System.out.println("Datos obtenidos del servicio: " + datosPrueba.size() + " registros");

            byte[] pdf = reporteService.generarPDFAnuncios(request);
            System.out.println("PDF generado exitosamente, tamaño: " + pdf.length + " bytes");

            String nombreArchivo = String.format("reporte_anuncios_%s_%s.pdf",
                    request.getFechaInicio() != null ? request.getFechaInicio().toString() : "todo",
                    request.getFechaFin() != null ? request.getFechaFin().toString() : "todo");

            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=" + nombreArchivo)
                    .build();

        } catch (Exception e) {
            System.err.println("=== ERROR EN PDF ANUNCIOS ===");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "N/A"));
            e.printStackTrace();

            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al generar PDF de anuncios: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    @Path("/ganancias-anunciante")
    public Response generarReporteGananciasAnunciante(ReporteGananciaAnuncianteRequest request) {
        try {
            List<ReporteGananciaAnunciante> resultado = reporteService.generarReporteGananciasAnunciante(request);
            return Response.ok(resultado).build();
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al generar reporte de ganancias por anunciante: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/salas-populares")
    public Response generarSalasPopulares(SalaPopularRequest request) {
        try {
            List<ReporteSalaPopularResponse> resultado = reporteService.generarSalasPopulares(request);
            return Response.ok(resultado).build();
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al generar reporte de salas populares: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/salas-comentadas")
    public Response generarSalasComentadas(ReporteSalaComentadaRequest request) {
        try {
            List<ReporteSalaComentadaResponse> resultado = reporteService.generarSalasComentadas(request);
            return Response.ok(resultado).build();
        } catch (DatosInvalidos e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error al generar reporte de salas comentadas: " + e.getMessage()))
                    .build();
        }
    }
}
