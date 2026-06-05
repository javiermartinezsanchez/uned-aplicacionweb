package es.alumno.uned;

import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import es.alumno.uned.config.AppProperties;
import jakarta.annotation.PostConstruct;

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
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppApplication.class);
		app.run(args);
	}

}
