package es.alumno.uned.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
 *  <p><u>Requisitos</u>
 *  <ul>Definir el el controlador (normalmente en el POST) los siguientes atributos:
 *  <li>url: Url para el commit</li>
 *  <li>urlCancel: Url para el cancel</li>
 *  <li>urlVista: La vista (thymeleaf que se va a mostrar</li>
 *  </ul>
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @ExceptionHandler(AlreadyExistException.class)
    public String handleAlreadyExist(AlreadyExistException ex, 
    		HttpServletRequest request,
    		Model model) {

    	String backUrl = request.getHeader("Referer");
		model.addAttribute("url", (String) model.getAttribute("url"));
		model.addAttribute("urlCancel", (String) model.getAttribute("urlCancel"));
    	model.addAttribute("backUrl", backUrl != null ? backUrl : "/");
        model.addAttribute("errorGlobal", messageSource.getMessage(
                ex.getMessage(),   // la clave del fichero "traducciones"
                ex.getArgs(),
                LocaleContextHolder.getLocale()
            ));
        model.addAttribute("form", ex.getDto());
        return (String) model.getAttribute("urlVista");
    }
}

