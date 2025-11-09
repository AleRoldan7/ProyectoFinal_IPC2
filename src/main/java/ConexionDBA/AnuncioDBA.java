/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

import Dtos.Anuncio.AnuncioResponse;
import EnumOptions.TipoAnuncio;
import ModeloEntidad.Anuncio.Anuncio;
import ModeloEntidad.Anuncio.AnuncioImagen;
import ModeloEntidad.Anuncio.AnuncioTexto;
import ModeloEntidad.Anuncio.AnuncioVideo;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
public class AnuncioDBA {

    private static final String AGREGAR_ANUNCIO_TEXTO_QUERY = "INSERT INTO anuncio (tipo_anuncio, mensaje_anuncio, nombre_anunciante,"
            + "fecha_inicio, dias_vigente, precio) VALUES (?,?,?,?,?,?)";

    private static final String AGREGAR_ANUNCIO_IMAGEN_QUERY = "INSERT INTO anuncio (tipo_anuncio, mensaje_anuncio, imagen_anuncio, "
            + "nombre_anunciante, fecha_inicio, dias_vigente, precio) VALUES (?,?,?,?,?,?,?)";

    private static final String AGREGAR_ANUNCIO_VIDEO_QUERY = "INSERT INTO anuncio (tipo_anuncio, mensaje_anuncio, video_anuncio, "
            + "nombre_anunciante, fecha_inicio, dias_vigente, precio) VALUES (?,?,?,?,?,?,?)";

    private static final String OBTENER_IMAGENID_QUERY = "SELECT imagen_anuncio FROM anuncio WHERE id_anuncio = ?";

    private static final String OBTENER_ANUNCIO_POR_ID_QUERY = "SELECT * FROM anuncio WHERE id_anuncio = ?";

    private static final String ACTIVAR_ANUNCIO_QUERY = "UPDATE anuncio SET anuncio_activo = ? WHERE id_anuncio = ?";

    private static final String DESACTIVAR_ANUNCIO_QUERY = "UPDATE anuncio SET anuncio_activo = FALSE, anuncio_vencido = TRUE WHERE id_anuncio = ?";

    private static final String OBTENER_ANUNCIOS_ACTIVOS_QUERY = "SELECT * FROM anuncio WHERE anuncio_activo = TRUE AND anuncio_vencido = FALSE";

    public void agregarAnuncioTexto(AnuncioTexto anuncioTexto) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(AGREGAR_ANUNCIO_TEXTO_QUERY)) {

            insert.setString(1, anuncioTexto.getTipoAnuncio().name());
            insert.setString(2, anuncioTexto.getMensajeAnuncio());
            insert.setInt(3, anuncioTexto.getIdUsuario());
            insert.setDate(4, Date.valueOf(anuncioTexto.getFechaInicio()));
            insert.setInt(5, anuncioTexto.getDiasVigente());
            insert.setDouble(6, anuncioTexto.getPrecio());
            insert.executeUpdate();

            System.out.println("Si lo agregoooo");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarAnuncioImagen(AnuncioImagen anuncioImagen) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(AGREGAR_ANUNCIO_IMAGEN_QUERY)) {

            insert.setString(1, anuncioImagen.getTipoAnuncio().name());
            insert.setString(2, anuncioImagen.getMensajeAnuncio());
            insert.setBytes(3, anuncioImagen.getImagenAnuncio());
            insert.setInt(4, anuncioImagen.getIdUsuario());
            insert.setDate(5, Date.valueOf(anuncioImagen.getFechaInicio()));
            insert.setInt(6, anuncioImagen.getDiasVigente());
            insert.setDouble(7, anuncioImagen.getPrecio());
            insert.executeUpdate();

            System.out.println("Si agrego imagen");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarAnuncioVideo(AnuncioVideo anuncioVideo) {

        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement insert = connection.prepareStatement(AGREGAR_ANUNCIO_VIDEO_QUERY)) {

            insert.setString(1, anuncioVideo.getTipoAnuncio().name());
            insert.setString(2, anuncioVideo.getMensajeAnuncio());
            insert.setString(3, anuncioVideo.getVideoAnuncio());
            insert.setInt(4, anuncioVideo.getIdUsuario());
            insert.setDate(5, Date.valueOf(anuncioVideo.getFechaInicio()));
            insert.setInt(6, anuncioVideo.getDiasVigente());
            insert.setDouble(7, anuncioVideo.getPrecio());
            insert.executeUpdate();

            System.out.println("Si jalo video");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public byte[] obtenerImagenId(int idAnuncio) {
        String sql = "SELECT imagen_anuncio FROM anuncio WHERE id_anuncio = ?";
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAnuncio);
            System.out.println("BUSCANDO IMAGEN PARA ID: " + idAnuncio);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Blob blob = rs.getBlob("imagen_anuncio");
                    if (blob != null && blob.length() > 0) {
                        byte[] bytes = blob.getBytes(1, (int) blob.length());
                        System.out.println("IMAGEN ENCONTRADA, TAMAÑO: " + bytes.length + " bytes");
                        return bytes;
                    } else {
                        System.out.println("BLOB NULO O VACÍO PARA ID: " + idAnuncio);
                    }
                } else {
                    System.out.println("NO SE ENCONTRÓ ANUNCIO CON ID: " + idAnuncio);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR SQL AL OBTENER IMAGEN ID " + idAnuncio);
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, Object>> getAnunciosPorUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT id_anuncio, tipo_anuncio, mensaje_anuncio, video_anuncio, "
                + "nombre_anunciante, fecha_inicio, dias_vigente, precio "
                + "FROM anuncio "
                + "WHERE nombre_anunciante = ? "
                + "ORDER BY fecha_inicio DESC";

        List<Map<String, Object>> lista = new ArrayList<>();
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> a = new HashMap<>();
                    a.put("idAnuncio", rs.getInt("id_anuncio"));
                    a.put("tipoAnuncio", rs.getString("tipo_anuncio"));
                    a.put("mensajeAnuncio", rs.getString("mensaje_anuncio"));
                    a.put("videoAnuncio", rs.getString("video_anuncio"));
                    a.put("nombreAnunciante", rs.getInt("nombre_anunciante"));
                    a.put("fechaInicio", rs.getDate("fecha_inicio").toString());
                    a.put("diasVigencia", rs.getInt("dias_vigente"));
                    a.put("precio", rs.getDouble("precio"));
                    lista.add(a);
                }
            }
        }
        return lista;
    }

    public void toggleAnuncioActivo(int idAnuncio, boolean activo) {
        String query = activo ? ACTIVAR_ANUNCIO_QUERY : DESACTIVAR_ANUNCIO_QUERY;
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setBoolean(1, activo);
            ps.setInt(2, idAnuncio);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AnuncioResponse> obtenerAnunciosActivos() {
        List<AnuncioResponse> lista = new ArrayList<>();
        try (Connection connection = Conexion.getInstance().getConnect(); PreparedStatement query = connection.prepareStatement(OBTENER_ANUNCIOS_ACTIVOS_QUERY); ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                AnuncioResponse a = new AnuncioResponse();
                a.setIdAnuncio(rs.getInt("id_anuncio"));
                a.setTipoAnuncio(TipoAnuncio.valueOf(rs.getString("tipo_anuncio")));
                a.setMensajeAnuncio(rs.getString("mensaje_anuncio"));
                a.setVideoAnuncio(rs.getString("video_anuncio"));
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public AnuncioResponse obtenerAnuncioPorId(int idAnuncio) {
        AnuncioResponse anuncio = null;
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(OBTENER_ANUNCIO_POR_ID_QUERY)) {

            ps.setInt(1, idAnuncio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    anuncio = new AnuncioResponse();
                    anuncio.setIdAnuncio(rs.getInt("id_anuncio"));
                    anuncio.setTipoAnuncio(TipoAnuncio.valueOf(rs.getString("tipo_anuncio")));
                    anuncio.setMensajeAnuncio(rs.getString("mensaje_anuncio"));
                    anuncio.setVideoAnuncio(rs.getString("video_anuncio"));
                    anuncio.setNombreAnunciante(rs.getInt("nombre_anunciante"));
                    anuncio.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                    anuncio.setDiasVigencia(rs.getInt("dias_vigente"));
                    anuncio.setPrecio(rs.getDouble("precio"));
                    anuncio.setAnuncioActivo(rs.getBoolean("anuncio_activo"));
                    anuncio.setAnuncioVencido(rs.getBoolean("anuncio_vencido"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anuncio;
    }

    private static final String OBTENER_ANUNCIOS_VISIBLES_QUERY
            = "SELECT * FROM anuncio "
            + "WHERE anuncio_activo = TRUE "
            + "AND anuncio_vencido = FALSE "
            + "AND CURDATE() BETWEEN fecha_inicio AND DATE_ADD(fecha_inicio, INTERVAL dias_vigente DAY)";

    public List<AnuncioResponse> obtenerAnunciosVisibles() {
        List<AnuncioResponse> lista = new ArrayList<>();
        try (Connection conn = Conexion.getInstance().getConnect(); PreparedStatement ps = conn.prepareStatement(OBTENER_ANUNCIOS_VISIBLES_QUERY); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AnuncioResponse a = new AnuncioResponse();
                a.setIdAnuncio(rs.getInt("id_anuncio"));
                a.setTipoAnuncio(TipoAnuncio.valueOf(rs.getString("tipo_anuncio")));
                a.setMensajeAnuncio(rs.getString("mensaje_anuncio"));
                a.setVideoAnuncio(rs.getString("video_anuncio"));
                a.setNombreAnunciante(rs.getInt("nombre_anunciante"));
                a.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                a.setDiasVigencia(rs.getInt("dias_vigente"));
                a.setPrecio(rs.getDouble("precio"));
                a.setAnuncioActivo(rs.getBoolean("anuncio_activo"));
                a.setAnuncioVencido(rs.getBoolean("anuncio_vencido"));
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
