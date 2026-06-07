package es.alumno.uned;

import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import es.alumno.uned.config.AppProperties;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

@ConfigurationPropertiesScan
@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
/**
 * Clase principal de arranque de la aplicación.
 * 
 * Se fuerza el "Locale" de España-español por compatibilidad con Docker.
 */
public class AppApplication extends SpringBootServletInitializer{

    @PostConstruct
    public void init() {
    	   Locale.setDefault(Locale.of("es", "ES")); 
    	    
    	    // Fuerza la zona horaria europea
    	    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Madrid"));
    }
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AppApplication.class);
     }
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Eleva el límite a 20,000 campos/parámetros en el Tomcat externo
        servletContext.setInitParameter("org.apache.tomcat.util.http.Parameters.MAX_COUNT", "20000");
        
        // Eleva el peso máximo permitido para el procesamiento de parámetros (10MB en bytes)
        servletContext.setInitParameter("org.apache.tomcat.util.http.Parameters.MAX_SIZE", "10485760");
        
        servletContext.setInitParameter("org.apache.catalina.filters.CSRF_NONCE_FILTER", "false");
        // Invoca obligatoriamente la inicialización por defecto de Spring
        super.onStartup(servletContext);
    }
 
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppApplication.class);
		app.run(args);
	}

}
