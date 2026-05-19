package es.alumno.uned.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.service.EstudianteCursoService;

@Controller
public class EstudianteCursoController {

	@Autowired
	EstudianteCursoService estudianteCursoService;
	@GetMapping("/estudiante/home")
	public String estudianteHome(@AuthenticationPrincipal SecurityUser userConnected,
			Model modelo) {
		
		
		return "estudiante/home";
	}

	@GetMapping("/estudiante/subscribircurso/{id}")
	public String subscribirCurso(@PathVariable Long id,
							     @AuthenticationPrincipal SecurityUser userConnected,
	                              RedirectAttributes redirectAttributes) {

	        
	        estudianteCursoService.subscribirAlumnoACurso(userConnected.getId(), id);
	        redirectAttributes.addFlashAttribute("success",
	                "Te has suscrito correctamente al curso.");

	    	//redirectAttributes.addFlashAttribute("error", e.getMessage());

	        //redirectAttributes.addFlashAttribute("error",
	        //        "No se pudo completar la suscripción.");

	    return "redirect:/viewcurso/" + id;
	}

}
