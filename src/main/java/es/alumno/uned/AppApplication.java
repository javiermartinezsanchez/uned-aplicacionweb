package es.alumno.uned;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import es.alumno.uned.config.AppProperties;

@ConfigurationPropertiesScan
@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class AppApplication extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // Esta línea es la clave:
        setRegisterErrorPageFilter(false); 
        return builder.sources(AppApplication.class)
        		// Desactiva el registro automático del filtro de errores
                // para que Tomcat tome el control total o Spring lo maneje internamente sin colisionar
                .properties("server.error.registration=bean");
    }
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppApplication.class);
		app.run(args);
	}

}
