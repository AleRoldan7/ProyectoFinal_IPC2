/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDBA;

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
            insert.setDouble(6, anuncioImagen.getPrecio());
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
            insert.setDouble(6, anuncioVideo.getPrecio());
            insert.executeUpdate();

            System.out.println("Si jalo video");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

}
