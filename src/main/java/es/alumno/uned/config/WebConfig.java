package es.alumno.uned.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
/**
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
 * 1.- Las ruta "externa" para guardar tanto imágenes como documentos que 
 * son enviados por el admin, profesor o los alumnos
 * </li>
 * <li>
 * 2.- La ruta de los recursos que se incluyen con la aplicación (banderas y otras imágenes)</li>
 * </ul>
 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    	
        registry.addResourceHandler("/images/curso/**")
        .addResourceLocations("file:" + appProperties.getUploadDir());
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");

    }

}
