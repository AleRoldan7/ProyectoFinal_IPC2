/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.CineDBA;
import Dtos.Cine.NewCineRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntityExists;
import ModeloEntidad.Cine.Cine;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class CineService {
    
    
    public Cine crearCine(NewCineRequest newCineRequest) throws DatosInvalidos, EntityExists {
        CineDBA cineDBA = new CineDBA();

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

}
