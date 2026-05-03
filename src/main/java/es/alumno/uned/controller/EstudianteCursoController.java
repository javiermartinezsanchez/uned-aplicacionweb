package es.alumno.uned.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.service.EstudianteCursoService;

@Controller
public class EstudianteCursoController {

	@Autowired
	EstudianteCursoService estudianteCursoService;
	
	@GetMapping("/subscribircurso/{id}")
	public String subscribirCurso(@PathVariable Long id,
	                              Principal principal,
	                              RedirectAttributes redirectAttributes) {

	    try {
	        String username = principal.getName();
	        estudianteCursoService.subscribirAlumnoACurso(username, id);
	        redirectAttributes.addFlashAttribute("success",
	                "Te has suscrito correctamente al curso.");

	    } catch (IllegalStateException e) {

	    	/* Excepciones que genera el Service de negocio:
	    	 * 	throw new IllegalStateException("El curso no existe.");
				throw new IllegalStateException("El estudiante no existe.");
				throw new IllegalStateException("Ya estás suscrito a este curso.");
				throw new IllegalStateException("El curso está bloqueado.");
	    	 * */
	    	redirectAttributes.addFlashAttribute("error", e.getMessage());

	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error",
	                "No se pudo completar la suscripción.");
	    }

	    return "redirect:/viewcurso/" + id;
	}

}
