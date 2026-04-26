package es.alumno.uned.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleUsuarioExistente(UserAlreadyExistException ex,
                                         Model model) {
        model.addAttribute("errorGlobal", ex.getMessage());
        return "usuario/form";
    }
    
    
}

