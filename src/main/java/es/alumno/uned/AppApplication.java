package es.alumno.uned;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import es.alumno.uned.config.AppProperties;

@ConfigurationPropertiesScan
@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class AppApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppApplication.class);
		app.run(args);
	}

}
