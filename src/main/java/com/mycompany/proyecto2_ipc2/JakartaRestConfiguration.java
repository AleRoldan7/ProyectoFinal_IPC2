package com.mycompany.proyecto2_ipc2;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures Jakarta RESTful Web Services for the application.
 *
 * @author Juneau
 */
@ApplicationPath("api/v1")
public class JakartaRestConfiguration extends ResourceConfig {

    public JakartaRestConfiguration() {

        packages("com.mycompany.proyecto2_ipc2");
        packages("ConexionDBA");
        packages("Controller");
        packages("Dtos.Anuncio");
        packages("Dtos.Cine");
        packages("Dtos.Usuario");
        packages("EnumOptions");
        packages("Excepciones");
        packages("ModeloEntidad.Anuncio");
        packages("ModeloEntidad.Cine");
        packages("ModeloEntidad.Usuario");
        packages("ModeloEntidad.Services");
        packages("com.mycompany.proyecto2_ipc2.resources");
        register(MultiPartFeature.class);

    }

}
