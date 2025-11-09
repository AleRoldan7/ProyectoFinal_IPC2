/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ReportesDBA;
import Dtos.Reportes.Boleto.ReporteBoletoRequest;
import Dtos.Reportes.Boleto.ReporteBoletoResponse;
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
import Excepciones.JasperException;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author alejandro
 */
public class ReporteService {

    private ReportesDBA reportesDBA = new ReportesDBA();

    public List<ReporteBoletoResponse> obtenerReporteBoletos(ReporteBoletoRequest reporteBoletoRequest) {
        return reportesDBA.reporteBoletos(reporteBoletoRequest);
    }

    public List<ReporteComentarioSalaResponse> obtenerComentario(NewReporteComentarioRequest newReporteComentarioRequest) {
        return reportesDBA.comentariosPorSala(newReporteComentarioRequest);
    }

    public byte[] generarPDF(ReporteBoletoRequest request) throws Exception {
        List<ReporteBoletoResponse> datos = obtenerReporteBoletos(request);

        Map<String, Object> params = new HashMap<>();
        params.put("titulo", "REPORTE DE VENTAS - "
                + (request.getUsuarioResponse() != null && "ADMIN_CINE".equals(request.getUsuarioResponse().getRolUsuario())
                ? "MI CINE" : "TODOS LOS CINES"));
        params.put("fechaGeneracion", new Date());
        params.put("totalBoletos", datos.size());
        params.put("totalRecaudado", datos.stream().mapToDouble(ReporteBoletoResponse::getPrecioBoleto).sum());
        params.put("cineNombre", datos.isEmpty() ? "N/A" : datos.get(0).getNombreCine());

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteBoleto.jasper");
        JasperPrint print = JasperFillManager.fillReport(jasper, params, new JRBeanCollectionDataSource(datos));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);
        return out.toByteArray();
    }

    public byte[] generarPDFComentariosSalas(NewReporteComentarioRequest req) throws Exception {
        List<ReporteComentarioSalaResponse> data = reportesDBA.obtenerComentariosSalas(req);
        Map<String, Object> params = new HashMap<>();
        params.put("TITULO", "REPORTE DE COMENTARIOS DE SALAS");
        params.put("FECHA", new Date());
        params.put("TOTAL_REGISTROS", data.size());

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteComentarioSala.jasper");
        if (jasper == null) {
            throw new Exception("Jasper no encontrado");
        }

        JasperPrint print = JasperFillManager.fillReport(jasper, params, new JRBeanCollectionDataSource(data));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);
        return out.toByteArray();
    }

    public List<ReportePeliculaResponse> obtenerPeliculasPorSala(NewReportePeliculaRequest req) {
        return reportesDBA.obtenerPeliculasPorSala(req);
    }

    public byte[] generarPDFPeliculas(NewReportePeliculaRequest req) throws Exception {
        List<ReportePeliculaResponse> data = obtenerPeliculasPorSala(req);
        Map<String, Object> params = new HashMap<>();
        params.put("TITULO", "REPORTE DE PELÍCULAS PROYECTADAS");
        params.put("FECHA_INICIO", req.getFechaInicio().toString());
        params.put("FECHA_FIN", req.getFechaFin().toString());
        params.put("TOTAL_REGISTROS", data.size());

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReportePelicula.jasper");
        if (jasper == null) {
            throw new Exception("Jasper no encontrado: ReportePeliculasSala.jasper");
        }

        JasperPrint print = JasperFillManager.fillReport(jasper, params, new JRBeanCollectionDataSource(data));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);
        return out.toByteArray();
    }

    public List<SalaResponse> obtenerSalasPorCine(int idCine) {
        return reportesDBA.obtenerSalasPorCine(idCine);
    }

    public List<ReporteSalaResponse> obtenerSalasGustadas(NewReporteSalaRequest newReporteSalaRequest) {
        return reportesDBA.obtenerSalasGustadas(newReporteSalaRequest);
    }

    public byte[] generarPDFSalasGustadas(NewReporteSalaRequest newReporteSalaRequest) throws Exception {
        List<ReporteSalaResponse> data = obtenerSalasGustadas(newReporteSalaRequest);
        Map<String, Object> params = new HashMap<>();
        params.put("TITULO", "TOP 5 SALAS MAS GUSTADAS");
        params.put("FECHA", new Date());
        params.put("TOTAL_REGISTROS", data.size());

        InputStream jasperStream = getClass().getResourceAsStream("/Reportes/ReporteSalasMasGustadas.jasper");
        if (jasperStream == null) {
            throw new Exception("No se encontró el archivo .jasper");
        }
        JasperPrint print = JasperFillManager.fillReport(jasperStream, params, new JRBeanCollectionDataSource(data));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);
        return out.toByteArray();
    }

    public List<ReporteGananciaResponse> generarReporteGanancias(NewReporteGananciaRequest request) throws DatosInvalidos {
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        return reportesDBA.generarReporteGanancias(request);
    }

    public List<ReporteAnunciosResponse> generarReporteAnuncios(NewReporteAnuncioRequest request) throws DatosInvalidos, SQLException {
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        return reportesDBA.generarReporteAnuncios(request);
    }

    public byte[] generarPDFAnuncios(NewReporteAnuncioRequest request) throws Exception {
        List<ReporteAnunciosResponse> data = generarReporteAnuncios(request);

        for (ReporteAnunciosResponse anuncio : data) {
            if (anuncio.getCosto() == null) {
                double costo = 0.0;
                switch (anuncio.getTipoAnuncio()) {
                    case "TEXTO":
                        costo = 50.0 * (anuncio.getDiasVigente() != null ? anuncio.getDiasVigente() : 0);
                        break;
                    case "TEXTO_IMAGEN":
                        costo = 75.0 * (anuncio.getDiasVigente() != null ? anuncio.getDiasVigente() : 0);
                        break;
                    case "TEXTO_VIDEO":
                        costo = 100.0 * (anuncio.getDiasVigente() != null ? anuncio.getDiasVigente() : 0);
                        break;
                }
                anuncio.setCosto(BigDecimal.valueOf(costo));
            }

            if (anuncio.getFechaFin() == null && anuncio.getFechaInicio() != null && anuncio.getDiasVigente() != null) {
                anuncio.setFechaFin(anuncio.getFechaInicio().plusDays(anuncio.getDiasVigente()));
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("TITULO", "REPORTE DE ANUNCIOS COMPRADOS");
        params.put("FECHA", new Date());
        params.put("TOTAL_REGISTROS", data.size());
        params.put("FECHA_INICIO", request.getFechaInicio() != null ? request.getFechaInicio().toString() : "Todos");
        params.put("FECHA_FIN", request.getFechaFin() != null ? request.getFechaFin().toString() : "Todos");
        params.put("TIPO_ANUNCIO", "Todos");

        double totalCostos = data.stream()
                .mapToDouble(item -> item.getCosto() != null ? item.getCosto().doubleValue() : 0.0)
                .sum();
        params.put("TOTAL_COSTOS", totalCostos);

        InputStream jasper = getClass().getResourceAsStream("/Reportes/ReporteAnunciosComprados.jasper");
        if (jasper == null) {
            throw new FileNotFoundException("No se encontró el archivo ReporteAnunciosComprados.jasper");
        }

        JasperPrint print = JasperFillManager.fillReport(jasper, params, new JRBeanCollectionDataSource(data));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, out);
        return out.toByteArray();
    }

    public List<ReporteGananciaAnunciante> generarReporteGananciasAnunciante(ReporteGananciaAnuncianteRequest request) throws DatosInvalidos {
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        return reportesDBA.generarReporteGananciasAnunciante(request);
    }

    public List<ReporteSalaPopularResponse> generarSalasPopulares(SalaPopularRequest request) throws DatosInvalidos, SQLException {
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        return reportesDBA.generarSalasPopulares(request);
    }

    public List<ReporteSalaComentadaResponse> generarSalasComentadas(ReporteSalaComentadaRequest request) throws DatosInvalidos {
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        return reportesDBA.generarSalasComentadas(request);
    }

    public List<ReporteBoletoResponse> reporteBoletos(ReporteBoletoRequest request) throws DatosInvalidos {
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        return reportesDBA.reporteBoletos(request);
    }

    public List<ReporteComentarioSalaResponse> comentariosPorSala(NewReporteComentarioRequest request) throws DatosInvalidos {
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        return reportesDBA.comentariosPorSala(request);
    }

    public List<ReporteComentarioSalaResponse> obtenerComentariosSalas(NewReporteComentarioRequest request) throws DatosInvalidos {
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        return reportesDBA.obtenerComentariosSalas(request);
    }

    private void validarFechas(LocalDate fechaInicio, LocalDate fechaFin) throws DatosInvalidos {
        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
            throw new DatosInvalidos("La fecha de inicio no puede ser posterior a la fecha fin");
        }
    }
}
