package es.alumno.uned.exception;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.service.AreaTematicaService;
import jakarta.servlet.http.HttpServletRequest;
/**
 * Control global de excepciones vinculadas a datos.
 * 
 * <p>Se generan en los "services" una vez que se validan los datos en los 
 * controladores normalmente com {@code @Valid}.
 * 
 * <p>Añadiremos tantos "handler" como Excepciones queramos controlar con 
 * este sistema
 * 
 * <p>Si queremos volver a una vista concreta deberemos recuperar sus datos
 *para que el modelo no genere errores.
 *
 *<p>Se genera <b>{@code model.addAttribute("errorGlobal", ex.getMessage())}</b>
 * para mostrar el mensaje en los formularios.
 * 
 * Se implementa el {@code MessageSource} para poder mostrar el mensaje de i18n 
 * 
 *  <p><b>Requisitos</b>
 *  Definir el el controlador (normalmente en el POST) los siguientes atributos en el Request:
 *  <ul>
 *  <li>url: Url para el commit</li>
 *  <li>urlCancel: Url para el cancel</li>
 *  <li>urlVista: La vista (thymeleaf que se va a mostrar)</li>
 *  </ul>
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    private AreaTematicaService areaTematicaService;
    
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    @ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
    
    @ModelAttribute("urlBuilder")
    public ServletUriComponentsBuilder urlBuilder() {
        // Retorna un constructor basado en la petición actual
        return ServletUriComponentsBuilder.fromCurrentRequest();
    }
    
    /**
     * Como el buscador de la home, está en el fragmento "header", es
     * común a todo el proyecto (excepto si hubiera alguna plantilla si el header.
     * <p>Como necesitamos poder conocer los parámetros y estos se incluyen
     * en un {@code Map<String, String>}, vamos a crearlos a nivel de "Adviser".
     * <p>De esta forma no se tendrá que incluir "siempre" este mapa en el modelo.
     * Y será Spring el encargado de manejarlo a nivel de {@link ModelAttribute} 
     * @param allParams Captura todos los parámetros de la query y lo devuelve, 
     * de forma que siempre existirá, aunque sea vacio.
     * @return Map con todos los parámetros o {} (vacio).
     */
    @ModelAttribute("paramsBusqueda")
    public Map<String, String> getParamsBusqueda(@RequestParam Map<String, String> allParams) {
         return allParams; 
    }
    
    /**
     * Para poder tener acceso al buscador desde todas las vistas que lo incluyan,
     * es necesario "injectar" entodo el Modelo la lista de áreas.
     * 
     * @return Lista de áreas con el nombre "areas".
     */
    @ModelAttribute("areas")
    public List<AreaTematicaDTO> getAreas() {
        return areaTematicaService.listAll();
    }
    @ExceptionHandler(AppGlobalException.class)
    public String handleAppException(AppGlobalException ex, 
    		HttpServletRequest request,
    		Model model) {
    	return putDataException(ex, request, model);
    }

	private String putDataException(AppGlobalException ex, HttpServletRequest request, Model model) {
		String backUrl = request.getHeader("Referer");
		model.addAttribute("viewName", (String) request.getAttribute("viewName"));
		model.addAttribute("url", (String) request.getAttribute("url"));
		model.addAttribute("urlCancel", (String) request.getAttribute("urlCancel"));
    	model.addAttribute("urlBack", backUrl != null ? backUrl : "/");
        model.addAttribute("errorGlobal", messageSource.getMessage(
                ex.getMessage(),   // la clave del fichero "traducciones"
                ex.getArgs(),
                LocaleContextHolder.getLocale()
            ));
        model.addAttribute("form", ex.getDto());
        return (String) request.getAttribute("viewName");
	}
}

