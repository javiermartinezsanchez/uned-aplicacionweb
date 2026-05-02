package es.alumno.uned.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria para encapsular los valores del fichero
 * de configuración application.properties
 * 
 * <p>Sólo leemos los valores que empiezen por "app" 
 * 
 */
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

	/**
	 * Definición del directorio en el que guardamos las imágenes y los ficheros
	 * que nos envían los estudiantes (entregables) y profesores
	 * (recursos de cursos).
	 * 
	 */
	private String uploadDir;
	
	public String getUploadDir() {
		
		return this.uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	
	
}
