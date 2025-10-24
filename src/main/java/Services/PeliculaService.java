/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConexionDBA.CineDBA;
import ConexionDBA.PeliculaDBA;
import ConexionDBA.SalaDBA;
import Dtos.Cine.NewPeliculaRequest;
import Dtos.Cine.NewPeliculaSalaRequest;
import Excepciones.DatosInvalidos;
import Excepciones.EntidadNotFound;
import Excepciones.EntityExists;
import ModeloEntidad.Cine.Pelicula;
import ModeloEntidad.Cine.SalaCine;

/**
 *
 * @author alejandro
 */
public class PeliculaService {

    private PeliculaDBA peliculaDBA = new PeliculaDBA();

    public Pelicula crearPelicula(NewPeliculaRequest newPeliculaRequest) throws DatosInvalidos, EntityExists {

        Pelicula entidaPelicula = extraer(newPeliculaRequest);

        if (peliculaDBA.existePelicula(entidaPelicula.getTituloPelicula())) {
            throw new EntityExists(
                    String.format("La Pelicula %s ya existe", entidaPelicula.getTituloPelicula())
            );
        }

        peliculaDBA.agregarPelicula(entidaPelicula);

        return entidaPelicula;
    }

    private Pelicula extraer(NewPeliculaRequest newPeliculaRequest) throws DatosInvalidos {

        try {

            Pelicula entPelicula = new Pelicula(
                    newPeliculaRequest.getTituloPelicula(),
                    newPeliculaRequest.getSinopsisPelicula(),
                    newPeliculaRequest.getDuracionPelicula(),
                    newPeliculaRequest.getCastPelicula(),
                    newPeliculaRequest.getDirectorPelicula(),
                    newPeliculaRequest.getPosterPelicula()
            );

            if (!entPelicula.esValido()) {
                throw new DatosInvalidos("Error en los datos enviados");
            }

            return entPelicula;
        } catch (Exception e) {
            throw new DatosInvalidos("Error en los datos enviados" + e.getMessage());
        }
    }

    public void agregarPeliculaSala(NewPeliculaSalaRequest request) throws DatosInvalidos, EntidadNotFound {
        if (request.getIdSala() == null || request.getIdPelicula() == null) {
            throw new DatosInvalidos("Faltan datos para asignar la película");
        }

        Integer idSalaCine = peliculaDBA.obtenerIdSalaCine(request.getIdSala());
        if (idSalaCine == null) {
            throw new EntidadNotFound("No se encontró sala_cine para id_sala=" + request.getIdSala());
        }

        peliculaDBA.asignarPeliculaSala(idSalaCine, request.getIdPelicula());
    }

}
