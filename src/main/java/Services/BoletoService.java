/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.BoletoDBA;
import ConexionDBA.UsuarioDBA;
import Dtos.Boleto.NewBoletoRequest;
import Dtos.Boleto.UpdateBoletoRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import Excepciones.SinDinero;
import ModeloEntidad.Cine.Boleto;
import ModeloEntidad.Usuario.Usuario;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alejandro
 */
public class BoletoService {

    private BoletoDBA boletoDBA = new BoletoDBA();
    private UsuarioDBA usuarioDBA = new UsuarioDBA();

    public Boleto comprarBoleto(NewBoletoRequest newBoletoRequest) throws DatosInvalidos, EntityExists, SinDinero {

        Boleto entidadBoleto = extraer(newBoletoRequest);

        Usuario usuario = usuarioDBA.obtenerUsuarioPorId(newBoletoRequest.getIdUsuario());
        if (usuario == null) {
            throw new DatosInvalidos("Usuario no encontrado.");
        }

        if (!usuarioDBA.descontarDineroUsuario(usuario.getIdUsuario(), newBoletoRequest.getPrecioBoleto())) {
            throw new DatosInvalidos("Saldo insuficiente para comprar el boleto");
        }
        boletoDBA.comprarBoleto(entidadBoleto);

        return entidadBoleto;
    }

    private Boleto extraer(NewBoletoRequest newBoletoRequest) throws DatosInvalidos {

        try {

            Boleto entidadBoleto = new Boleto(
                    newBoletoRequest.getIdUsuario(),
                    newBoletoRequest.getIdPeliculaSala(),
                    newBoletoRequest.getPrecioBoleto(),
                    newBoletoRequest.getFilaAsiento(),
                    newBoletoRequest.getColumnaAsiento(),
                    newBoletoRequest.getFechaCompra()
            );

            return entidadBoleto;
        } catch (Exception e) {
            throw new DatosInvalidos("Error en los datos enviados" + e.getMessage());
        }

    }

    public List<Map<String, Object>> obtenerPeliculasPorCine(String buscar) throws SQLException {
        return boletoDBA.obtenerPeliculasPorCine(buscar);
    }

    public void actualizarBoleto(UpdateBoletoRequest updateBoletoRequest) throws DatosInvalidos, EntidadNotFound, EntityExists {

        if (updateBoletoRequest.getIdBoleto() == null || updateBoletoRequest.getIdBoleto() <= 0) {
            throw new DatosInvalidos("El ID de la película no es válido.");
        }

        if (!boletoDBA.existeBoletoId(updateBoletoRequest.getIdBoleto())) {
            throw new EntidadNotFound(
                    String.format("No se encontró ningun boleto con ID = %d", updateBoletoRequest.getIdBoleto())
            );
        }

        boletoDBA.actualizarBoleto(updateBoletoRequest);

    }

    public List<Map<String, Object>> obtenerPeliculasCompradas(int idUsuario) {
        return new BoletoDBA().obtenerPeliculasCompradas(idUsuario);
    }
}
