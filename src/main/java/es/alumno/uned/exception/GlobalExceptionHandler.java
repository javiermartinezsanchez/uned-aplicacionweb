package es.alumno.uned.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleUsuarioExistente(UserAlreadyExistException ex,
    						HttpServletRequest request,
    						Model model) {
    	
    	String backUrl = request.getHeader("Referer");
        model.addAttribute("backUrl", backUrl != null ? backUrl : "/");
        model.addAttribute("errorGlobal", ex.getMessage());
        return "usuario/form";
    }
    
    
}

