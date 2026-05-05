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
	 * Definición del directorio en el que guardamos las imágenes y los documentos
	 * que nos envían los estudiantes (entregables) y profesores
	 * (recursos de cursos).
	 * 
	 * Posteriormente se mapean a direcciones lógicas.
	 * 
	 */
	private String uploadImgDir;
	private String uploadDocDir;
	
	public String getUploadImgDir() {
		
		return this.uploadImgDir;
	}

	public void setUploadImgDir (String uploadImgDir) {
		this.uploadImgDir = uploadImgDir;
	}
	
	public String getUploadDocDir() {
		
		return this.uploadDocDir;
	}

	public void setUploadDocDir(String uploadDocDir) {
		this.uploadDocDir = uploadDocDir;
	}
}
