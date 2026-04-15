package es.alumno.uned.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * Componente de configuración para los resources estáticos (js y css)
 * de la librería webjars.
 * 
 * Nos define las versiones de las librerías utilizadas de Bootstrap y de JQUery 
 * para ser utilizados en las vistas de Thymeleaf.
 */
@Component
@ConfigurationProperties(prefix = "webjars")
public class WebjarsConfig {

	String bootstrap = "5.3.3";
	String jquery;
	public String getBootstrap() {
		return bootstrap;
	}
	public void setBootstrap(String bootstrap) {
		this.bootstrap = bootstrap;
	}
	public String getJquery() {
		return jquery;
	}
	public void setJquery(String jquery) {
		this.jquery = jquery;
	}
}
