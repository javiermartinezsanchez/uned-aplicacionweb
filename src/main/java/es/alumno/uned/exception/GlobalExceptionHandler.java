package es.alumno.uned.exception;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

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

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    @ExceptionHandler(AppGlobalException.class)
    public String handleAppException(AppGlobalException ex, 
    		HttpServletRequest request,
    		Model model) {
    	return putDataException(ex, request, model);
    }
    @ExceptionHandler(FileSizeExcedeedException.class)
    public String handleFileSizeException(FileSizeExcedeedException ex, HttpServletRequest request,
    		Model model) {
    	String backUrl = request.getHeader("Referer");
        model.addAttribute("urlBack", backUrl != null ? backUrl : "/");
        
        String mensajeTraducido = messageSource.getMessage(
            "error.archivo.demasiado_grande", 
            null, 
            LocaleContextHolder.getLocale()
        );
        
        FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
        if (flashMap != null) {
            // Guardamos el error directamente en la infraestructura de Spring
            flashMap.put("errorGlobal", mensajeTraducido);
            flashMap.put("form", ex.getDto());
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
        model.addAttribute("errorGlobal", messageSource.getMessage(
                ex.getMessage(),   // la clave del fichero "traducciones"
                ex.getArgs(),
                LocaleContextHolder.getLocale()
            ));
        model.addAttribute("form", ex.getDto());
        return (String) request.getAttribute("viewName");
	}
}

