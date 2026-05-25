package es.alumno.uned.controller;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
/**
 * Para el control de los errores 404,500 y redirigir a las vistas propias.
 */
@Controller
public class CustomErrorController implements ErrorController {
	/**
	 * Manejador "Handler" el error.. 
	 * @param request La Request que genera el error.
	 * @param model Modelo de la vista que genera el error.
	 * @return Devuelve la vista que lo muestra
	 */
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        Object uri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Object servlet = request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        System.out.println(">>> ERROR STATUS: " + status);
        System.out.println(">>> ERROR URI: " + uri);
        System.out.println(">>> ERROR SERVLET: " + servlet);
        System.out.println(">>> ERROR EXCEPTION: " + exception);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);

            switch (statusCode) {
                case 404:
                    model.addAttribute("message", "La página no existe");
                    return "error/error-404";

                case 500:
                    model.addAttribute("message", "Error interno del servidor");
                    return "error/error-500";

                default:
                    model.addAttribute("message", "Ha ocurrido un error inesperado");
                    return "error/error";
            }
        }

        model.addAttribute("message", "Ha ocurrido un error desconocido");
        return "error";
    }
}
