package es.alumno.uned.controller;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);

            switch (statusCode) {
                case 404:
                    model.addAttribute("message", "La página no existe");
                    return "error-404";

                case 500:
                    model.addAttribute("message", "Error interno del servidor");
                    return "error-500";

                default:
                    model.addAttribute("message", "Ha ocurrido un error inesperado");
                    return "error";
            }
        }

        model.addAttribute("message", "Ha ocurrido un error desconocido");
        return "error";
    }
}
