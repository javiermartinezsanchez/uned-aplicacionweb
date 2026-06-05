package es.alumno.uned.exception;


import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
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
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
	@Autowired
    private AreaTematicaService areaTematicaService;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    @ExceptionHandler(AppGlobalException.class)
    public String handleAppException(AppGlobalException ex, 
    		HttpServletRequest request,
    		Model model) {
    	return putDataException(ex, request, model);
    }
    
    @ExceptionHandler({
    	FileSizeExcedeedException.class, 
    	SinDTOException.class}
    )
    public String handleFileSizeException(Exception ex, HttpServletRequest request,
    		Model model) {
    	String backUrl = request.getHeader("Referer");
        model.addAttribute("urlBack", backUrl != null ? backUrl : "/");
     
        FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
        if (flashMap != null) {
            // Guardamos el error directamente en la infraestructura de Spring
            
            // Aprovechando que utilizamos Java 21 utilizamos "Patter maching"
            switch (ex) {
            case FileSizeExcedeedException fileException -> 
                {flashMap.put("form", fileException.getDto());
                flashMap.put("errorGlobal", getMsgLocale(ex.getMessage()));
                }
            case SinDTOException sinDtoException -> {
            	flashMap.put("errorGlobal", getMsgLocale(sinDtoException.getMessage(), sinDtoException.getArgs()));
            }
            default -> { 
            	flashMap.put("errorGlobal", getMsgLocale(ex.getMessage()));
            }
            }

        }

        if (backUrl != null) {
            return "redirect:" + backUrl;
        }
        
        return "redirect:/";
    }

    private String putDataException(AppGlobalException ex, HttpServletRequest request, Model model) {
		String backUrl = request.getHeader("Referer");
		
		
		model.addAttribute("viewName", (String) request.getAttribute("viewName"));
		model.addAttribute("url", (String) request.getAttribute("url"));
		model.addAttribute("urlCancel", (String) request.getAttribute("urlCancel"));
    	model.addAttribute("urlBack", backUrl != null ? backUrl : "/");
        model.addAttribute("errorGlobal", getMsgLocale(ex.getMessage(), ex.getArgs()));
        model.addAttribute("form", ex.getDto());
        recuperaUrlBuilder( model,  request);
        recuperaParamsBusqueda(model, request);
        if (request.getAttribute("viewName") == null) {
        	return "redirect:" + backUrl;
        	
        }
        
        return (String) request.getAttribute("viewName");
	}
	/**
	 * Nos devuelve el mensaje traducido al locale.
	 * @param claveMensaje Clave existente en "traducciones.properties"
	 * @param args Argumentos opcionales si la clave es compuesta.
	 * @return Texto traducido.
	 */
    private String getMsgLocale(String claveMensaje, Object...args) {
    	Locale locale = LocaleContextHolder.getLocale();

        Object[] argsTraducidos = Arrays.stream(args)
                .map(arg -> arg instanceof String key ? messageSource.getMessage(key, null, key, locale) : arg)
                .toArray();

        return messageSource.getMessage(claveMensaje, argsTraducidos, locale);
	}
    
	/**
	 * <p>Inyecta en el modelo una variable de tipo texto (String) llamada currentUri que contiene la ruta exacta de la petición HTTP actual, excluyendo el dominio y los parámetros de consulta (query strings).
	 * 
	 * <p>Recibe el objeto HttpServletRequest de la petición en curso y extrae el Path mediante el método getRequestURI()
	 * @param request HttpRequest realizado.
	 * @return URI de la petición (request).
	 */
	@ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
    
	/**
	 * Inyecta en el modelo un objeto constructor de URLs (ServletUriComponentsBuilder) guardado bajo la variable urlBuilder. 
	 * <p>Este objeto viene precargado con todos los datos de la petición HTTP actual (protocolo, dominio, puerto, ruta y parámetros).
	 * <p>Utiliza el contexto estático de Spring Web para capturar la petición activa (fromCurrentRequest()) y expone esta factoría dinámica directamente a la vista.
	 * <p><b>Utilidad en las vistas (Thymeleaf/JSP)</b>
	 * <p>Nos sirve para crear enlaces de paginación o filtros dinámicos.
	 * <p>Permite a la vista modificar o añadir parámetros a la URL actual sin perder los que ya existían.
	 * <p>Se elimina el parámetro "lang" para no propagarlo en todas las URL's.
	 * @return
	 */
	@ModelAttribute("urlBuilder")
	public ServletUriComponentsBuilder urlBuilder() {
	    return ServletUriComponentsBuilder.fromCurrentRequest();
	}

    
    /**
     * Como el buscador de la home, está en el fragmento "header", es
     * común a todo el proyecto (excepto si hubiera alguna plantilla sin el header).
     * <p>En las páginas con buscador que generan un lista de elementos, al seleccionar una y volver al mismo, "necesitamos" recuperar los datos de dicho buscador.
     * <p>Incluimos todos los parámetros en un {@code Map<String, String>}, disponible por todos los @Controller y las vistas asociadas.
     * <p>Spring lo maneja a nivel de {@link ModelAttribute} 
     * @param allParams Captura todos los parámetros de la query y lo devuelve, 
     * de forma que siempre existirá, aunque sea vacio.
     * @return Map con todos los parámetros o {} (vacio).
     */
    @ModelAttribute("paramsBusqueda")
    public Map<String, String> getParamsBusqueda(@RequestParam(required = false) Map<String, String> allParams) {
    	 if (allParams == null || allParams.isEmpty()) {
             return new HashMap<>();
         }
         return new HashMap<>(allParams); 
    }
    
    /**
     * Injectamos la lista de áreas en todos los "Model".
     * <p>Permite tener accesible esta lista para el Buscador general de la "Home" y para otros lugares que sea necesario.
     * <p><b>NOTA:</b>
     * <p>En los casos que existan dos listas en una misma página, aunque estén en formularios diferentes, no se podrá utilizar el {@code getElementById()} ya que encontrará varios elementos con el mismo.
     * 
     * @return Lista de áreas con el nombre "areas".
     */
    @ModelAttribute("areas")
    public List<AreaTematicaDTO> getAreas() {
        return ordenadaPorTitulo(areaTematicaService.listAll());
    }
    /**
     * Ordenamos la lisa de áreas temáticas por Título
     * @param listaAreas Lista obtenida de la BD.
     * @return Lista ordenada.
     */
    private List<AreaTematicaDTO> ordenadaPorTitulo(List<AreaTematicaDTO> listaAreas){
    	listaAreas.sort(Comparator.comparing(AreaTematicaDTO :: getTitulo));
    	return listaAreas;
    }

    /**
     * Método "último recurso" para recuperar el urlBuilder después de una excepción.
     * @param model Modelo de la vista
     * @param request HttPServletRequest que ha generado la excepción.
     */
    private void recuperaUrlBuilder(Model model, HttpServletRequest request) {
    	   try {
    	        model.addAttribute("urlBuilder", ServletUriComponentsBuilder.fromRequest(request));
    	    } catch (Exception e) {
    	        // Fallback seguro en caso de que el contexto de la petición esté corrupto tras la excepción
    	        model.addAttribute("urlBuilder", ServletUriComponentsBuilder.fromContextPath(request));
    	    }

    }
    
    /**
     * Método "último recurso" para recuperar el valor de paramsBusqueda después de una excepción.
     * @param model Modelo de la vista
     * @param request HttPServletRequest que ha generado la excepción.
     */
 	private void recuperaParamsBusqueda(Model model, HttpServletRequest request) {
		   Map<String, String> params = new HashMap<>();
		   Map<String, String[]> requestMap = request.getParameterMap();
		    if (requestMap != null) {
		        for (Map.Entry<String, String[]> entry : requestMap.entrySet()) {
		            // Tomamos el primer valor del array de parámetros
		            if (entry.getValue() != null && entry.getValue().length > 0) {
		                params.put(entry.getKey(), entry.getValue()[0]);
		            }
		        }
		    }
		    model.addAttribute("paramsBusqueda", params);
		
	}

}

