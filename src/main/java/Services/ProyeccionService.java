/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.ProyectarDBA;
import Dtos.Proyeccion.NewProyeccionRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import java.sql.SQLException;

/**
 *
 * @author alejandro
 */
public class ProyeccionService {

    private ProyectarDBA proyectarDBA = new ProyectarDBA();

    public void asignarProyeccion(NewProyeccionRequest newProyeccionRequest, int idUsuario) throws DatosInvalidos, EntidadNotFound {

        if (newProyeccionRequest.getIdSala() == null || newProyeccionRequest.getIdPelicula() == null) {
            throw new DatosInvalidos("Faltan sala o película");
        }
        if (newProyeccionRequest.getFechaInicio().isAfter(newProyeccionRequest.getFechaFin())) {
            throw new DatosInvalidos("Fecha inicio no puede ser después de fecha fin");
        }
        try {
            proyectarDBA.asignarPeliculaSala(newProyeccionRequest, idUsuario);
        } catch (SQLException e) {
            throw new DatosInvalidos("Error al crear proyección: " + e.getMessage());

        }
    }
}
