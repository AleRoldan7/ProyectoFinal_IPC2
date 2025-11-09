/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.BloqueoAnuncioDBA;
import ConexionDBA.CineDBA;
import ConexionDBA.UsuarioDBA;
import Dtos.Anuncio.Bloqueo.BloqueoAnuncioResponse;
import Dtos.Anuncio.Bloqueo.NewBloqueoAnuncioRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import ModeloEntidad.Anuncio.BloqueoAnuncio;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class BloqueoAnuncioService {

    private BloqueoAnuncioDBA bloqueoDBA = new BloqueoAnuncioDBA();
    private CineDBA cineDBA = new CineDBA();
    private UsuarioDBA usuarioDBA = new UsuarioDBA();

    public BloqueoAnuncioResponse crearBloqueoAnuncios(NewBloqueoAnuncioRequest newBloqueoAnuncioRequest)
            throws DatosInvalidos, EntidadNotFound, SQLException {

        Integer idCineUsuario = usuarioDBA.obtenerCineUsuario(newBloqueoAnuncioRequest.getIdUsuario());
        if (idCineUsuario == null || !idCineUsuario.equals(newBloqueoAnuncioRequest.getIdCine())) {
            throw new EntidadNotFound("El usuario no es administrador de este cine");
        }

        if (bloqueoDBA.tieneBloqueoActivo(newBloqueoAnuncioRequest.getIdCine())) {
            throw new DatosInvalidos("El cine ya tiene un bloqueo de anuncios activo");
        }

        double costoDiario = bloqueoDBA.obtenerCostoBloqueoDiario(newBloqueoAnuncioRequest.getIdCine());
        double costoTotal = costoDiario * newBloqueoAnuncioRequest.getDiasBloqueo();

        if (!bloqueoDBA.descontarDineroCine(newBloqueoAnuncioRequest.getIdCine(), costoTotal)) {
            throw new DatosInvalidos("Fondos insuficientes en la cartera del cine");
        }

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(newBloqueoAnuncioRequest.getDiasBloqueo());

        BloqueoAnuncio bloqueo = new BloqueoAnuncio(
                newBloqueoAnuncioRequest.getIdCine(),
                newBloqueoAnuncioRequest.getDiasBloqueo(),
                costoTotal,
                fechaInicio,
                fechaFin,
                newBloqueoAnuncioRequest.getFechaPago()
        );

        if (!bloqueo.esValido()) {
            throw new DatosInvalidos("Datos del bloqueo no válidos");
        }

        bloqueoDBA.crearBloqueo(bloqueo);

        return new BloqueoAnuncioResponse(
                bloqueo.getIdBloqueo(),
                bloqueo.getIdCine(),
                bloqueo.getDiasBloqueo(),
                bloqueo.getCostoTotal(),
                bloqueo.getFechaInicio(),
                bloqueo.getFechaFin(),
                bloqueo.getFechaPago(),
                bloqueo.isActivo(),
                "Bloqueo de anuncios creado exitosamente"
        );
    }

    public void actualizarCostoBloqueo(Integer idCine, double nuevoCosto, Integer idAdminSistema)
            throws DatosInvalidos, EntidadNotFound, SQLException {

        if (!usuarioDBA.obtenerUsuarioPorId(idAdminSistema).getRolUsuario().name().equals("ADMIN_SISTEMA")) {
            throw new DatosInvalidos("Solo los administradores del sistema pueden cambiar el costo");
        }

        if (!cineDBA.existeCineID(idCine)) {
            throw new EntidadNotFound("Cine no encontrado");
        }

        if (nuevoCosto <= 0) {
            throw new DatosInvalidos("El costo debe ser mayor a cero");
        }

        bloqueoDBA.actualizarCostoBloqueo(idCine, nuevoCosto);
    }
}
