/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.CineDBA;
import ConexionDBA.SalaDBA;
import Dtos.Cine.NewSalaRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import ModeloEntidad.Cine.Sala;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class SalaService {

    public Sala crearSala(NewSalaRequest newSalaRequest) throws DatosInvalidos, EntityExists {
        SalaDBA salaDBA = new SalaDBA();

        Sala entidadSala = extraer(newSalaRequest);

        if (salaDBA.existeSala(entidadSala.getNombreSala())) {
            throw new EntityExists(
                    String.format("La sala con nombre %s ya existe", entidadSala.getNombreSala())
            );
        }

        salaDBA.crearSala(entidadSala);

        Integer idCine = new CineDBA().obtenerIdCineAdmin(entidadSala.getIdUsuario());

        if (idCine != null) {

            salaDBA.asignarSalaCine(entidadSala.getIdSala(), idCine);

        } else {
            throw new DatosInvalidos("El administrador no tiene un cine asignado");

        }
        return entidadSala;
    }

    private Sala extraer(NewSalaRequest newSalaRequest) throws DatosInvalidos {

        try {
            Sala entidadSala = new Sala(
                    newSalaRequest.getIdUsuario(),
                    newSalaRequest.getNombreSala(),
                    newSalaRequest.getFilaSala(),
                    newSalaRequest.getColumnaSala(),
                    newSalaRequest.getFechaCreacion()
            );

            if (!entidadSala.esValido()) {
                throw new DatosInvalidos("Error en los datos enviados");
            }

            return entidadSala;
        } catch (Exception e) {
            throw new DatosInvalidos("Error en los datos enviados" + e.getMessage());
        }

    }

}
