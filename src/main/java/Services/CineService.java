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
            throw  new EntityExists(
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

        if (updateCineRequest.getIdCine()== null || updateCineRequest.getIdCine()<= 0) {
            throw new DatosInvalidos("El ID de la película no es válido.");
        }

        if (!cineDBA.existeCineID(updateCineRequest.getIdCine())) {
            throw new EntidadNotFound(
                    String.format("No se encontró ningun cine con ID = %d", updateCineRequest.getIdCine())
            );
        }

        cineDBA.actualizarCine(updateCineRequest);

    }

}
