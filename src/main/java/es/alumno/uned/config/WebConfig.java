package es.alumno.uned.config;


import java.util.Locale;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
/**
 * Clase utilitaria para la "customización" de SpringMVC.
 * 
 * <p>Añadimos un interceptor para la detección del lenguaje del usuario (Locale).
 * <p>Añadimos los manejadores de recursos para los documentos e imágenes que son subidos por alumnos y profesores.
 *  
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final AppProperties appProperties;

	public WebConfig(AppProperties appProperties) {
		this.appProperties = appProperties;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver resolver = new SessionLocaleResolver();
	    resolver.setDefaultLocale(Locale.forLanguageTag("es-ES"));
	    return resolver;
	}


    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
	/**
	 * Añadimos las rutas de las imágenes.
	 * 
	 * <ul><li>
	 * 1.- La ruta "externa" para guardar imágenes 
	 * 2.- La ruta "externa" para guardar documentos que 
	 * son enviados por el admin, profesor o los alumnos
	 * </li>
	 * <li>
	 * 3.- La ruta de los recursos que se incluyen con la aplicación (banderas y otras imágenes)</li>
	 * </ul>
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    	
        registry.addResourceHandler("/images/curso/**")
        .addResourceLocations("file:" + appProperties.getUploadImgDir());

        registry.addResourceHandler("/docs/curso/**")
        .addResourceLocations("file:" + appProperties.getUploadDocDir());

        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");

    }
    
    /**
     * Se genera este Filtro para detectar la excepción del tamaño de los ficheros
     * uploados (por Admin-Profesor-Estudiantes).
     * <p>Se establece en "application.properties" el máximo
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        resolver.setResolveLazily(true); // Permite que el error llegue al ControllerAdvice
        return resolver;
    }
}
