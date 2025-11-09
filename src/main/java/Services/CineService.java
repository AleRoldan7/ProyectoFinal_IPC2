/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.CineDBA;
import Dtos.Cine.NewCineRequest;
import Dtos.Cine.UpdateCineRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import ModeloEntidad.Cine.Cine;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class CineService {

    private CineDBA cineDBA = new CineDBA();

    public Cine crearCine(NewCineRequest newCineRequest) throws DatosInvalidos, EntityExists {

        Cine entidadCine = extraer(newCineRequest);

        if (cineDBA.adminAsigandoCine(entidadCine.getIdUsuario())) {
            throw new EntityExists(
                    String.format("El usuario ya administra un cine", entidadCine.getIdUsuario()));
        }

        if (cineDBA.existeCine(entidadCine.getNombreCine())) {
            throw new EntityExists(
                    String.format("El nombre del cine %s ya existe", entidadCine.getNombreCine())
            );
        }

        cineDBA.crearCine(entidadCine);

        return entidadCine;
    }

    private Cine extraer(NewCineRequest newCineRequest) throws DatosInvalidos {

        try {

            Cine entidadCine = new Cine(
                    newCineRequest.getNombreCine(),
                    newCineRequest.getIdUsuario(),
                    newCineRequest.getFechaCreacion(),
                    newCineRequest.getCostoCine()
            );

            if (!entidadCine.esValido()) {
                throw new DatosInvalidos("Error en los datos enviados");
            }

            return entidadCine;
        } catch (Exception e) {
            throw new DatosInvalidos("Error en los datos enviados" + e.getMessage());
        }

    }

    public void actualizarCine(UpdateCineRequest updateCineRequest) throws DatosInvalidos, EntidadNotFound, EntityExists {

        if (updateCineRequest.getIdCine() == null || updateCineRequest.getIdCine() <= 0) {
            throw new DatosInvalidos("El ID de la película no es válido.");
        }

        if (!cineDBA.existeCineID(updateCineRequest.getIdCine())) {
            throw new EntidadNotFound(
                    String.format("No se encontró ningun cine con ID = %d", updateCineRequest.getIdCine())
            );
        }

        cineDBA.actualizarCine(updateCineRequest);

    }

    public void recargarCarteraDelCine(int idCine, double monto, int idUsuario) throws DatosInvalidos, EntidadNotFound {
        if (monto <= 0) {
            throw new DatosInvalidos("Monto inválido");
        }

        boolean esAdmin = cineDBA.esAdminDelCine(idCine, idUsuario);
        System.out.println("Es admin del cine: " + esAdmin);

        if (!esAdmin) {
            System.out.println("Usuario no es admin del cine");
            throw new RuntimeException("Solo el administrador del cine puede recargar la cartera");
        }
        
        cineDBA.recargarCartera(idCine, monto);
        System.out.println("si recargoooo");
    }

    public void bloquearAnuncios(int idCine, int dias, int idUsuario) throws DatosInvalidos, EntidadNotFound {
        if (dias <= 0) {
            throw new DatosInvalidos("Días inválidos");
        }
        if (!cineDBA.esAdminDelCine(idCine, idUsuario)) {
            throw new DatosInvalidos("Solo el administrador del cine puede bloquear anuncios");
        }

        double costoPorDia = cineDBA.obtenerCostoBloqueo(idCine);
        double total = costoPorDia * dias;
        double saldo = cineDBA.obtenerSaldoCartera(idCine);

        if (saldo < total) {
            throw new DatosInvalidos("Saldo insuficiente en la cartera del cine. Necesitas Q" + total);
        }

        LocalDate inicio = LocalDate.now();
        LocalDate fin = inicio.plusDays(dias);

        cineDBA.descontarCartera(idCine, total);
        cineDBA.registrarBloqueo(idCine, dias, inicio, fin, total);
    }
}
