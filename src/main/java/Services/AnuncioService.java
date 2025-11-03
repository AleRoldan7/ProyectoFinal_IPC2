/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.AnuncioDBA;
import ConexionDBA.UsuarioDBA;
import Dtos.Anuncio.NewAnuncioRequest;
import EnumOptions.TipoAnuncio;
import Excepciones.DatosInvalidos;
import ModeloEntidad.Anuncio.AnuncioImagen;
import ModeloEntidad.Anuncio.AnuncioTexto;
import ModeloEntidad.Anuncio.AnuncioVideo;
import ModeloEntidad.Usuario.Usuario;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class AnuncioService {

    private AnuncioDBA anuncioDBA = new AnuncioDBA();
    private UsuarioDBA usuarioDBA = new UsuarioDBA();
    
    public void crearAnuncio(NewAnuncioRequest newAnuncioRequest) throws DatosInvalidos {

        try {
            
            double precio = calcularPrecio(newAnuncioRequest.getTipoAnuncio(), newAnuncioRequest.getDiasVigencia());
            newAnuncioRequest.setPrecio(precio);
            
            boolean tieneDinero = usuarioDBA.comprarAnuncio(newAnuncioRequest.getNombreAnunciante(), precio);
            
            if (!tieneDinero) {
                throw new DatosInvalidos("No hay suficiente dinero en la cartera para crear el anuncio");
            }
            
            switch (newAnuncioRequest.getTipoAnuncio()) {
                case TEXTO:
                    AnuncioTexto anuncioTexto = new AnuncioTexto(
                            null,
                            newAnuncioRequest.getMensajeAnuncio(),
                            newAnuncioRequest.getNombreAnunciante(),
                            newAnuncioRequest.getFechaInicio(),
                            newAnuncioRequest.getDiasVigencia(),
                            newAnuncioRequest.getPrecio()
                    );
                    anuncioDBA.agregarAnuncioTexto(anuncioTexto);
                    break;

                case TEXTO_IMAGEN:

                    byte[] imagen = null;
                    if (newAnuncioRequest.getImagenAnuncio() != null) {

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        int datosRead;
                        byte[] data = new byte[16384];

                        while ((datosRead = newAnuncioRequest.getImagenAnuncio().read(data, 0, data.length)) != -1) {
                            byteArrayOutputStream.write(data, 0, datosRead);
                        }
                        byteArrayOutputStream.flush();
                        imagen = byteArrayOutputStream.toByteArray();
                    }

                    AnuncioImagen anuncioImagen = new AnuncioImagen(
                            null,
                            newAnuncioRequest.getMensajeAnuncio(),
                            imagen,
                            newAnuncioRequest.getNombreAnunciante(),
                            newAnuncioRequest.getFechaInicio(),
                            newAnuncioRequest.getDiasVigencia(),
                            newAnuncioRequest.getPrecio()
                    );
                    anuncioDBA.agregarAnuncioImagen(anuncioImagen);
                    break;

                case TEXTO_VIDEO:
                    AnuncioVideo anuncioVideo = new AnuncioVideo(
                            null,
                            newAnuncioRequest.getMensajeAnuncio(),
                            newAnuncioRequest.getVideoAnuncio(),
                            newAnuncioRequest.getNombreAnunciante(),
                            newAnuncioRequest.getFechaInicio(),
                            newAnuncioRequest.getDiasVigencia(),
                            newAnuncioRequest.getPrecio()
                    );
                    anuncioDBA.agregarAnuncioVideo(anuncioVideo);
                    break;

                default:
                    throw new DatosInvalidos("Tipo de anuncio no valido");
            }
        } catch (Exception e) {
            usuarioDBA.recargarCartera(String.valueOf(newAnuncioRequest.getNombreAnunciante()), newAnuncioRequest.getPrecio());
            throw new DatosInvalidos("Error al crear anuncio " + e.getMessage());
        }
    }

    private double calcularPrecio(TipoAnuncio tipoAnuncio, int diasVigencia) {
        
        double precioBase;
        
        switch (tipoAnuncio) {
            case TEXTO:
                precioBase = 10;
                break;
            case TEXTO_IMAGEN:
                precioBase = 20;
                break;
            case TEXTO_VIDEO:
                precioBase = 30;
                break;
            default:
                precioBase = 0;
        }
        return precioBase * diasVigencia; 
    }

}
