/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

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
import Dtos.Usuario.UsuarioResponse;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author alejandro
 */
public class ReportesDBA {

    private static final String REPORTE_BOLETOS_QUERY
            = "SELECT "
            + "b.id_boleto AS idBoleto, "
            + "s.id_sala, "
            + "s.nombre_sala, "
            + "u.id_usuario, "
            + "u.nombre AS nombre_usuario, "
            + "u.user_name, "
            + "pe.titulo_pelicula, "
            + "pr.hora AS horaPelicula, "
            + "b.precio_boleto, "
            + "b.fila_asiento, "
            + "b.columna_asiento, "
            + "b.fecha_compra, "
            + "c.id_cine, "
            + "c.nombre_cine "
            + "FROM boleto_compra b "
            + "JOIN usuario u ON b.id_usuario = u.id_usuario "
            + "JOIN proyecta_pelicula pr ON b.id_proyeccion = pr.id_proyecta "
            + "JOIN pelicula pe ON pr.id_pelicula = pe.id_pelicula "
            + "JOIN sala_cine sc ON pr.id_sala_cine = sc.id_sala_cine "
            + "JOIN sala s ON sc.id_sala = s.id_sala "
            + "JOIN cine c ON sc.id_cine = c.id_cine "
            + "WHERE 1=1 ";

    private static final String COMENTARIOS_SALA
            = "SELECT s.nombre_sala, cs.comentario, cs.calificacion_sala, cs.fecha_comentario, u.nombre "
            + "FROM comentario_sala cs "
            + "JOIN sala_cine sc ON cs.id_sala_cine = sc.id_sala_cine "
            + "JOIN sala s ON sc.id_sala = s.id_sala "
            + "JOIN usuario u ON cs.id_usuario = u.id_usuario "
            + "WHERE sc.id_cine = ? "
            + "AND (? IS NULL OR sc.id_sala_cine = ?) "
            + "AND DATE(cs.fecha_comentario) BETWEEN ? AND ? "
            + "ORDER BY cs.fecha_comentario DESC";

    private static final String PELICULAS_POR_SALA
            = "SELECT "
            + "s.nombre_sala AS nombreSala, "
            + "p.titulo_pelicula AS tituloPelicula, "
            + "pp.fecha_inicio AS fecha "
            + "FROM proyecta_pelicula pp "
            + "JOIN pelicula p ON pp.id_pelicula = p.id_pelicula "
            + "JOIN sala_cine sc ON pp.id_sala_cine = sc.id_sala_cine "
            + "JOIN sala s ON sc.id_sala = s.id_sala "
            + "JOIN cine c ON sc.id_cine = c.id_cine "
            + "WHERE c.id_cine = ? "
            + "AND (? IS NULL OR sc.id_sala_cine = ?) "
            + "AND pp.fecha_inicio >= ? "
            + "AND pp.fecha_inicio <= ? "
            + "ORDER BY s.nombre_sala, pp.fecha_inicio";

    private static final String SALAS_POR_CINE
            = "SELECT "
            + "sc.id_sala_cine AS idSalaCine, "
            + "s.nombre_sala AS nombreSala "
            + "FROM sala_cine sc "
            + "JOIN sala s ON sc.id_sala = s.id_sala "
            + "WHERE sc.id_cine = ? "
            + "ORDER BY s.nombre_sala";

    private static final String SALAS_GUSTADAS_QUERY
            = "SELECT "
            + "s.nombre_sala AS nombreSala, "
            + "AVG(cs.calificacion_sala) AS promedio, "
            + "COUNT(cs.calificacion_sala) AS total "
            + "FROM comentario_sala cs "
            + "JOIN sala_cine sc ON cs.id_sala_cine = sc.id_sala_cine "
            + "JOIN sala s ON sc.id_sala = s.id_sala "
            + "WHERE sc.id_cine = ? "
            + "AND (? IS NULL OR cs.fecha_comentario >= ?) "
            + "AND (? IS NULL OR cs.fecha_comentario <= ?) "
            + "GROUP BY s.id_sala, s.nombre_sala "
            + "ORDER BY promedio DESC "
            + "LIMIT 5";

    private static final String REPORTE_GANANCIAS_QUERY
            = "SELECT "
            + "    c.nombre_cine, "
            + "    COALESCE(SUM(c.costo_cine), 0) as costo_cine, "
            + "    COALESCE(SUM(ba.costo_total), 0) as ingresos_bloqueos, "
            + "    COALESCE(SUM("
            + "        CASE "
            + "            WHEN a.tipo_anuncio = 'TEXTO' THEN 50 * a.dias_vigente "
            + "            WHEN a.tipo_anuncio = 'TEXTO_IMAGEN' THEN 75 * a.dias_vigente "
            + "            WHEN a.tipo_anuncio = 'TEXTO_VIDEO' THEN 100 * a.dias_vigente "
            + "            ELSE 0 "
            + "        END"
            + "    ), 0) as ingresos_anuncios, "
            + "    COALESCE(SUM(c.costo_cine), 0) as total_costos, "
            + "    COALESCE(SUM(ba.costo_total), 0) + COALESCE(SUM("
            + "        CASE "
            + "            WHEN a.tipo_anuncio = 'TEXTO' THEN 50 * a.dias_vigente "
            + "            WHEN a.tipo_anuncio = 'TEXTO_IMAGEN' THEN 75 * a.dias_vigente "
            + "            WHEN a.tipo_anuncio = 'TEXTO_VIDEO' THEN 100 * a.dias_vigente "
            + "            ELSE 0 "
            + "        END"
            + "    ), 0) as total_ingresos, "
            + "    (COALESCE(SUM(ba.costo_total), 0) + COALESCE(SUM("
            + "        CASE "
            + "            WHEN a.tipo_anuncio = 'TEXTO' THEN 50 * a.dias_vigente "
            + "            WHEN a.tipo_anuncio = 'TEXTO_IMAGEN' THEN 75 * a.dias_vigente "
            + "            WHEN a.tipo_anuncio = 'TEXTO_VIDEO' THEN 100 * a.dias_vigente "
            + "            ELSE 0 "
            + "        END"
            + "    ), 0)) - COALESCE(SUM(c.costo_cine), 0) as ganancia "
            + "FROM cine c "
            + "LEFT JOIN bloqueo_anuncios ba ON c.id_cine = ba.id_cine "
            + "    AND ba.fecha_pago BETWEEN ? AND ? "
            + "LEFT JOIN anuncio_cine ac ON c.id_cine = ac.id_cine "
            + "LEFT JOIN anuncio a ON ac.id_anuncio = a.id_anuncio "
            + "    AND a.fecha_inicio BETWEEN ? AND ? "
            + "GROUP BY c.id_cine, c.nombre_cine";

    private static final String REPORTE_ANUNCIOS_QUERY
            = "SELECT "
            + "    a.id_anuncio, "
            + "    a.tipo_anuncio, "
            + "    a.mensaje_anuncio, "
            + "    u.nombre as anunciante, "
            + "    a.fecha_inicio, "
            + "    a.fecha_final, "
            + "    a.dias_vigente, "
            + "    CASE "
            + "        WHEN a.tipo_anuncio = 'TEXTO' THEN 50 * a.dias_vigente "
            + "        WHEN a.tipo_anuncio = 'TEXTO_IMAGEN' THEN 75 * a.dias_vigente "
            + "        WHEN a.tipo_anuncio = 'TEXTO_VIDEO' THEN 100 * a.dias_vigente "
            + "        ELSE 0 "
            + "    END as costo, "
            + "    a.anuncio_activo "
            + "FROM anuncio a "
            + "JOIN usuario u ON a.nombre_anunciante = u.id_usuario "
            + "WHERE a.fecha_inicio BETWEEN ? AND ?";

    private static final String REPORTE_GANANCIAS_ANUNCIANTE_QUERY
            = "SELECT "
            + "    u.nombre as nombre_anunciante, "
            + "    SUM(CASE "
            + "        WHEN a.tipo_anuncio = 'TEXTO' THEN 50 * a.dias_vigente "
            + "        WHEN a.tipo_anuncio = 'TEXTO_IMAGEN' THEN 75 * a.dias_vigente "
            + "        WHEN a.tipo_anuncio = 'TEXTO_VIDEO' THEN 100 * a.dias_vigente "
            + "        ELSE 0 "
            + "    END) as total_gastado, "
            + "    COUNT(a.id_anuncio) as total_anuncios "
            + "FROM usuario u "
            + "JOIN anuncio a ON u.id_usuario = a.nombre_anunciante "
            + "WHERE a.fecha_inicio BETWEEN ? AND ?";

    private static final String REPORTE_SALAS_POPULARES_QUERY
            = "SELECT "
            + "    s.nombre_sala, "
            + "    c.nombre_cine, "
            + "    AVG(cs.calificacion_sala) as promedio_calificacion, "
            + "    COUNT(DISTINCT bc.id_boleto) as total_boletos "
            + "FROM sala s "
            + "JOIN sala_cine sc ON s.id_sala = sc.id_sala "
            + "JOIN cine c ON sc.id_cine = c.id_cine "
            + "LEFT JOIN comentario_sala cs ON sc.id_sala_cine = cs.id_sala_cine "
            + "    AND cs.fecha_comentario BETWEEN ? AND ? "
            + "LEFT JOIN proyecta_pelicula pp ON sc.id_sala_cine = pp.id_sala_cine "
            + "LEFT JOIN boleto_compra bc ON pp.id_proyecta = bc.id_proyeccion "
            + "    AND bc.fecha_compra BETWEEN ? AND ? "
            + "WHERE cs.calificacion_sala IS NOT NULL";

    private static final String REPORTE_SALAS_COMENTADAS_QUERY
            = "SELECT "
            + "    s.nombre_sala, "
            + "    c.nombre_cine, "
            + "    COUNT(cs.id_comentario) as total_comentarios "
            + "FROM sala s "
            + "JOIN sala_cine sc ON s.id_sala = sc.id_sala "
            + "JOIN cine c ON sc.id_cine = c.id_cine "
            + "JOIN comentario_sala cs ON sc.id_sala_cine = cs.id_sala_cine "
            + "WHERE cs.fecha_comentario BETWEEN ? AND ?";

    public List<ReporteBoletoResponse> reporteBoletos(ReporteBoletoRequest request) {
        List<ReporteBoletoResponse> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(REPORTE_BOLETOS_QUERY);
        List<Object> params = new ArrayList<>();

        if (request.getFechaInicio() != null && request.getFechaFin() != null) {
            sql.append(" AND b.fecha_compra BETWEEN ? AND ? ");
            params.add(Date.valueOf(request.getFechaInicio()));
            params.add(Date.valueOf(request.getFechaFin()));
        }

        if (request.getIdCine() != null && request.getIdCine() > 0) {
            sql.append(" AND c.id_cine = ? ");
            params.add(request.getIdCine());
        }

        if (request.getIdSala() != null && request.getIdSala() > 0) {
            sql.append(" AND s.id_sala = ? ");
            params.add(request.getIdSala());
        }

        UsuarioResponse user = request.getUsuarioResponse();
        if (user != null && "ADMIN_CINE".equals(user.getRolUsuario())) {
            sql.append(" AND c.id_cine = (SELECT id_cine FROM cine WHERE id_admin = ?) ");
            params.add(user.getIdUsuario());
        }

        sql.append(" ORDER BY b.fecha_compra DESC, b.id_boleto DESC");

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof Date) {
                    ps.setDate(i + 1, (Date) p);
                } else {
                    ps.setInt(i + 1, (Integer) p);
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteBoletoResponse r = new ReporteBoletoResponse();

                r.setIdBoleto(rs.getInt("idBoleto"));
                r.setIdSala(rs.getInt("id_sala"));
                r.setNombreSala(rs.getString("nombre_sala"));
                r.setTituloPelicula(rs.getString("titulo_pelicula"));
                r.setHoraPelicula(rs.getString("horaPelicula"));
                r.setPrecioBoleto(rs.getDouble("precio_boleto"));
                r.setFilaAsiento(rs.getInt("fila_asiento"));
                r.setColumnaAsiento(rs.getInt("columna_asiento"));
                r.setFechaCompra(rs.getDate("fecha_compra").toLocalDate());
                r.setIdCine(rs.getInt("id_cine"));
                r.setNombreCine(rs.getString("nombre_cine"));

                UsuarioResponse u = new UsuarioResponse();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre_usuario"));
                u.setUserName(rs.getString("user_name"));
                r.setUsuarioResponse(u);

                lista.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error en reporte: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<ReporteComentarioSalaResponse> comentariosPorSala(NewReporteComentarioRequest newReporteComentarioRequest) {
        List<ReporteComentarioSalaResponse> lista = new ArrayList<>();
        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement query = con.prepareStatement(COMENTARIOS_SALA)) {
            query.setInt(1, newReporteComentarioRequest.getIdCine());
            query.setObject(2, newReporteComentarioRequest.getIdSala());
            query.setObject(3, newReporteComentarioRequest.getIdSala());
            query.setDate(4, Date.valueOf(newReporteComentarioRequest.getFechaInicio()));
            query.setDate(5, Date.valueOf(newReporteComentarioRequest.getFechaFin()));

            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                ReporteComentarioSalaResponse r = new ReporteComentarioSalaResponse();
                r.setNombreSala(resultSet.getString("nombre_sala"));
                r.setComentario(resultSet.getString("comentario"));
                r.setCalificacion(resultSet.getInt("calificacion_sala"));
                r.setFecha(resultSet.getDate("fecha_comentario").toLocalDate());
                r.setUsuario(resultSet.getString("nombre"));
                lista.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ReporteComentarioSalaResponse> obtenerComentariosSalas(NewReporteComentarioRequest req) {
        List<ReporteComentarioSalaResponse> lista = new ArrayList<>();

        if (req.getIdCine() == null) {
            return lista;
        }

        String query
                = "SELECT "
                + "    s.nombre_sala AS nombreSala, "
                + "    cs.comentario, "
                + "    cs.calificacion_sala AS calificacion, "
                + "    cs.fecha_comentario AS fecha, "
                + "    u.nombre AS usuario "
                + "FROM comentario_sala cs "
                + "JOIN sala_cine sc ON cs.id_sala_cine = sc.id_sala_cine "
                + "JOIN sala s ON sc.id_sala = s.id_sala "
                + "JOIN usuario u ON cs.id_usuario = u.id_usuario "
                + "JOIN cine c ON sc.id_cine = c.id_cine "
                + "WHERE c.id_cine = ? "
                + "  AND (? IS NULL OR sc.id_sala_cine = ?) "
                + "  AND (? IS NULL OR DATE(cs.fecha_comentario) >= ?) "
                + "  AND (? IS NULL OR DATE(cs.fecha_comentario) <= ?) "
                + "ORDER BY cs.fecha_comentario DESC";

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, req.getIdCine());
            ps.setObject(2, req.getIdSala());
            ps.setObject(3, req.getIdSala());
            ps.setObject(4, req.getFechaInicio() != null ? Date.valueOf(req.getFechaInicio()) : null);
            ps.setObject(5, req.getFechaInicio() != null ? Date.valueOf(req.getFechaInicio()) : null);
            ps.setObject(6, req.getFechaFin() != null ? Date.valueOf(req.getFechaFin()) : null);
            ps.setObject(7, req.getFechaFin() != null ? Date.valueOf(req.getFechaFin()) : null);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteComentarioSalaResponse r = new ReporteComentarioSalaResponse();
                r.setNombreSala(rs.getString("nombreSala"));
                r.setComentario(rs.getString("comentario"));
                r.setCalificacion(rs.getInt("calificacion"));
                r.setFecha(rs.getDate("fecha").toLocalDate());
                r.setUsuario(rs.getString("usuario"));
                lista.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ReportePeliculaResponse> obtenerPeliculasPorSala(NewReportePeliculaRequest req) {
        List<ReportePeliculaResponse> lista = new ArrayList<>();

        if (req.getIdCine() == null || req.getFechaInicio() == null || req.getFechaFin() == null) {
            return lista;
        }

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(PELICULAS_POR_SALA)) {

            ps.setInt(1, req.getIdCine());
            ps.setObject(2, req.getIdSala());
            ps.setObject(3, req.getIdSala());
            ps.setDate(4, Date.valueOf(req.getFechaInicio()));
            ps.setDate(5, Date.valueOf(req.getFechaFin()));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReportePeliculaResponse r = new ReportePeliculaResponse();
                r.setNombreSala(rs.getString("nombreSala"));
                r.setTituloPelicula(rs.getString("tituloPelicula"));
                r.setFecha(rs.getDate("fecha").toLocalDate());
                lista.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error en consulta SQL: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<SalaResponse> obtenerSalasPorCine(int idCine) {
        List<SalaResponse> lista = new ArrayList<>();
        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(SALAS_POR_CINE)) {

            ps.setInt(1, idCine);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SalaResponse sala = new SalaResponse();
                sala.setIdSala(rs.getInt("idSalaCine"));
                sala.setNombreSala(rs.getString("nombreSala"));
                lista.add(sala);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ReporteSalaResponse> obtenerSalasGustadas(NewReporteSalaRequest newReporteSalaRequest) {
        List<ReporteSalaResponse> todas = new ArrayList<>();

        if (newReporteSalaRequest.getIdCine() == null) {
            return todas;
        }

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(SALAS_GUSTADAS_QUERY)) {

            query.setInt(1, newReporteSalaRequest.getIdCine());
            query.setObject(2, newReporteSalaRequest.getFechaInicio());
            query.setObject(3, newReporteSalaRequest.getFechaInicio());
            query.setObject(4, newReporteSalaRequest.getFechaFin());
            query.setObject(5, newReporteSalaRequest.getFechaFin());

            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                ReporteSalaResponse reporteSalaResponse = new ReporteSalaResponse();
                reporteSalaResponse.setNombreSala(resultSet.getString("nombreSala"));
                reporteSalaResponse.setPromedioCalificacion(resultSet.getDouble("promedio"));
                reporteSalaResponse.setCalificacion(resultSet.getInt("total"));
                todas.add(reporteSalaResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return todas;
    }

    public List<ReporteGananciaResponse> generarReporteGanancias(NewReporteGananciaRequest request) {
        List<ReporteGananciaResponse> lista = new ArrayList<>();

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(REPORTE_GANANCIAS_QUERY)) {

            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFin()));
            ps.setDate(3, Date.valueOf(request.getFechaInicio()));
            ps.setDate(4, Date.valueOf(request.getFechaFin()));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteGananciaResponse response = new ReporteGananciaResponse();
                response.setNombreCine(rs.getString("nombre_cine"));
                response.setCostoCine(rs.getDouble("costo_cine"));
                response.setIngresosAnuncios(rs.getDouble("ingresos_anuncios"));
                response.setIngresosBloqueos(rs.getDouble("ingresos_bloqueos"));
                response.setTotalCostos(rs.getDouble("total_costos"));
                response.setTotalIngresos(rs.getDouble("total_ingresos"));
                response.setGanancia(rs.getDouble("ganancia"));
                lista.add(response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error en reporte de ganancias: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<ReporteAnunciosResponse> generarReporteAnuncios(NewReporteAnuncioRequest request) throws SQLException {
        List<ReporteAnunciosResponse> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(REPORTE_ANUNCIOS_QUERY);
        List<Object> params = new ArrayList<>();

        params.add(Date.valueOf(request.getFechaInicio()));
        params.add(Date.valueOf(request.getFechaFin()));

        if (request.getTipoAnuncio() != null && !request.getTipoAnuncio().isEmpty()) {
            sql.append(" AND a.tipo_anuncio = ?");
            params.add(request.getTipoAnuncio());
        }

        sql.append(" ORDER BY a.fecha_inicio DESC");

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteAnunciosResponse response = new ReporteAnunciosResponse();
                response.setIdAnuncio(rs.getInt("id_anuncio"));
                response.setTipoAnuncio(rs.getString("tipo_anuncio"));
                response.setMensaje(rs.getString("mensaje_anuncio"));
                response.setAnunciante(rs.getString("anunciante"));

                // Manejar fechas nulas
                Date fechaInicio = rs.getDate("fecha_inicio");
                Date fechaFin = rs.getDate("fecha_final");

                if (fechaInicio != null) {
                    response.setFechaInicio(fechaInicio.toLocalDate());
                }

                if (fechaFin != null) {
                    response.setFechaFin(fechaFin.toLocalDate());
                }

                response.setDiasVigente(rs.getInt("dias_vigente"));

                response.setActivo(rs.getBoolean("anuncio_activo"));
                lista.add(response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error en reporte de anuncios: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<ReporteGananciaAnunciante> generarReporteGananciasAnunciante(ReporteGananciaAnuncianteRequest request) {
        List<ReporteGananciaAnunciante> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(REPORTE_GANANCIAS_ANUNCIANTE_QUERY);
        List<Object> params = new ArrayList<>();

        params.add(Date.valueOf(request.getFechaInicio()));
        params.add(Date.valueOf(request.getFechaFin()));

        if (request.getIdAnunciante() != null) {
            sql.append(" AND u.id_usuario = ?");
            params.add(request.getIdAnunciante());
        }

        sql.append(" GROUP BY u.id_usuario, u.nombre");

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteGananciaAnunciante response = new ReporteGananciaAnunciante();
                response.setNombreAnunciante(rs.getString("nombre_anunciante"));
                response.setTotalGastado(rs.getDouble("total_gastado"));
                response.setTotalAnuncios(rs.getInt("total_anuncios"));

                List<ReporteGananciaAnunciante.AnuncioDetalle> anuncios
                        = obtenerDetallesAnuncios(request.getFechaInicio(), request.getFechaFin(),
                                rs.getString("nombre_anunciante"));
                response.setAnuncios(anuncios);

                lista.add(response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error en reporte de ganancias por anunciante: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<ReporteSalaPopularResponse> generarSalasPopulares(SalaPopularRequest request) throws SQLException {
        List<ReporteSalaPopularResponse> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(REPORTE_SALAS_POPULARES_QUERY);
        List<Object> params = new ArrayList<>();

        params.add(Timestamp.valueOf(request.getFechaInicio().atStartOfDay()));
        params.add(Timestamp.valueOf(request.getFechaFin().atTime(23, 59, 59)));
        params.add(Date.valueOf(request.getFechaInicio()));
        params.add(Date.valueOf(request.getFechaFin()));

        if (request.getIdCine() != null) {
            sql.append(" AND c.id_cine = ?");
            params.add(request.getIdCine());
        }

        sql.append(" GROUP BY s.id_sala, s.nombre_sala, c.nombre_cine");
        sql.append(" HAVING AVG(cs.calificacion_sala) IS NOT NULL");
        sql.append(" ORDER BY promedio_calificacion DESC, total_boletos DESC");
        sql.append(" LIMIT 5");

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteSalaPopularResponse response = new ReporteSalaPopularResponse();
                response.setNombreSala(rs.getString("nombre_sala"));
                response.setNombreCine(rs.getString("nombre_cine"));
                response.setPromedioCalificacion(rs.getDouble("promedio_calificacion"));
                response.setTotalBoletos(rs.getInt("total_boletos"));

                // Obtener usuarios que compraron boletos
                List<ReporteSalaPopularResponse.UsuarioBoleto> usuarios
                        = obtenerUsuariosBoletosSala(rs.getString("nombre_sala"), request.getFechaInicio(), request.getFechaFin());
                response.setUsuarios(usuarios);

                lista.add(response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error en reporte de salas populares: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<ReporteSalaComentadaResponse> generarSalasComentadas(ReporteSalaComentadaRequest request) {
        List<ReporteSalaComentadaResponse> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(REPORTE_SALAS_COMENTADAS_QUERY);
        List<Object> params = new ArrayList<>();

        params.add(Timestamp.valueOf(request.getFechaInicio().atStartOfDay()));
        params.add(Timestamp.valueOf(request.getFechaFin().atTime(23, 59, 59)));

        if (request.getIdCine() != null) {
            sql.append(" AND c.id_cine = ?");
            params.add(request.getIdCine());
        }

        sql.append(" GROUP BY s.id_sala, s.nombre_sala, c.nombre_cine");
        sql.append(" ORDER BY total_comentarios DESC");
        sql.append(" LIMIT 5");

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteSalaComentadaResponse response = new ReporteSalaComentadaResponse();
                response.setNombreSala(rs.getString("nombre_sala"));
                response.setNombreCine(rs.getString("nombre_cine"));
                response.setTotalComentarios(rs.getInt("total_comentarios"));

                List<ReporteSalaComentadaResponse.ComentarioDetalle> comentarios
                        = obtenerComentariosSala(rs.getString("nombre_sala"), request.getFechaInicio(), request.getFechaFin());
                response.setComentarios(comentarios);

                lista.add(response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error en reporte de salas comentadas: " + e.getMessage(), e);
        }
        return lista;
    }

    private List<ReporteGananciaAnunciante.AnuncioDetalle> obtenerDetallesAnuncios(
            LocalDate fechaInicio, LocalDate fechaFin, String nombreAnunciante) {
        List<ReporteGananciaAnunciante.AnuncioDetalle> detalles = new ArrayList<>();

        String sql = "SELECT a.id_anuncio, a.tipo_anuncio, a.dias_vigente, "
                + "CASE WHEN a.tipo_anuncio = 'TEXTO' THEN 50 * a.dias_vigente "
                + "     WHEN a.tipo_anuncio = 'TEXTO_IMAGEN' THEN 75 * a.dias_vigente "
                + "     WHEN a.tipo_anuncio = 'TEXTO_VIDEO' THEN 100 * a.dias_vigente "
                + "     ELSE 0 END as costo "
                + "FROM anuncio a JOIN usuario u ON a.nombre_anunciante = u.id_usuario "
                + "WHERE u.nombre = ? AND a.fecha_inicio BETWEEN ? AND ?";

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreAnunciante);
            ps.setDate(2, Date.valueOf(fechaInicio));
            ps.setDate(3, Date.valueOf(fechaFin));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteGananciaAnunciante.AnuncioDetalle detalle
                        = new ReporteGananciaAnunciante.AnuncioDetalle();
                detalle.setIdAnuncio(rs.getInt("id_anuncio"));
                detalle.setTipoAnuncio(rs.getString("tipo_anuncio"));
                detalle.setCosto(rs.getDouble("costo"));
                detalle.setPeriodo(rs.getInt("dias_vigente") + " días");
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    private List<ReporteSalaPopularResponse.UsuarioBoleto> obtenerUsuariosBoletosSala(
            String nombreSala, LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {

        List<ReporteSalaPopularResponse.UsuarioBoleto> usuarios = new ArrayList<>();

        String sql = "SELECT u.nombre, COUNT(bc.id_boleto) as boletos_comprados "
                + "FROM boleto_compra bc "
                + "JOIN usuario u ON bc.id_usuario = u.id_usuario "
                + "JOIN proyecta_pelicula pp ON bc.id_proyeccion = pp.id_proyecta "
                + "JOIN sala_cine sc ON pp.id_sala_cine = sc.id_sala_cine "
                + "JOIN sala s ON sc.id_sala = s.id_sala "
                + "WHERE s.nombre_sala = ? AND bc.fecha_compra BETWEEN ? AND ? "
                + "GROUP BY u.id_usuario, u.nombre "
                + "ORDER BY boletos_comprados DESC "
                + "LIMIT 10";

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreSala);
            ps.setDate(2, Date.valueOf(fechaInicio));
            ps.setDate(3, Date.valueOf(fechaFin));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteSalaPopularResponse.UsuarioBoleto usuario
                        = new ReporteSalaPopularResponse.UsuarioBoleto();
                usuario.setNombreUsuario(rs.getString("nombre"));
                usuario.setBoletosComprados(rs.getInt("boletos_comprados"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    private List<ReporteSalaComentadaResponse.ComentarioDetalle> obtenerComentariosSala(
            String nombreSala, LocalDate fechaInicio, LocalDate fechaFin) {
        List<ReporteSalaComentadaResponse.ComentarioDetalle> comentarios = new ArrayList<>();

        String sql = "SELECT u.nombre, cs.comentario, cs.calificacion_sala, cs.fecha_comentario "
                + "FROM comentario_sala cs "
                + "JOIN usuario u ON cs.id_usuario = u.id_usuario "
                + "JOIN sala_cine sc ON cs.id_sala_cine = sc.id_sala_cine "
                + "JOIN sala s ON sc.id_sala = s.id_sala "
                + "WHERE s.nombre_sala = ? AND cs.fecha_comentario BETWEEN ? AND ? "
                + "ORDER BY cs.fecha_comentario DESC "
                + "LIMIT 10";

        try (Connection con = Conexion.getInstance().getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreSala);
            ps.setTimestamp(2, Timestamp.valueOf(fechaInicio.atStartOfDay()));
            ps.setTimestamp(3, Timestamp.valueOf(fechaFin.atTime(23, 59, 59)));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteSalaComentadaResponse.ComentarioDetalle comentario
                        = new ReporteSalaComentadaResponse.ComentarioDetalle();
                comentario.setUsuario(rs.getString("nombre"));
                comentario.setComentario(rs.getString("comentario"));
                comentario.setCalificacion(rs.getInt("calificacion_sala"));
                comentario.setFecha(rs.getTimestamp("fecha_comentario").toString());
                comentarios.add(comentario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comentarios;
    }
}
