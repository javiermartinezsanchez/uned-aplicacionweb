package es.alumno.uned.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.dto.EstudianteCursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.service.CursoService;
import es.alumno.uned.service.EstudianteCursoService;
import es.alumno.uned.service.EstudianteService;

@Controller
public class EstudianteCursoController extends BaseCrudController {

	@Autowired
	EstudianteCursoService estudianteCursoService;
	@Autowired
	CursoService cursoService;
	@Autowired
	EstudianteService estudianteService;
	
	
	@GetMapping("/estudiante/home")
	public String estudianteHome(@AuthenticationPrincipal SecurityUser userConnected,
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(name="page", defaultValue = "0") int page,
			Model modelo) {
		
		//String rol = userConnected.getRol(); // ESTUD

		paramsBusqueda.put("estudianteId", userConnected.getId().toString());
		Paginacion<EstudianteCurso, EstudianteCursoDTO> paginacion = estudianteCursoService.listadoPaginado( getParams( 0, 3), paramsToMap(paramsBusqueda));
		Paginacion<Curso, CursoDTO> paginacionCD =cursoService.listadoCursosDisponiblesPorAreas(  getParams( page, 3), userConnected.getId(), getAreasEstudiante( userConnected.getId() ));

		
		modelo.addAttribute("paginacion", paginacion);
		modelo.addAttribute("paginacionCD", paginacionCD);


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

	@GetMapping("/estudiante/cursos/ajax/miscursos")
	public String paginarMisCursosAJAX(
			@AuthenticationPrincipal SecurityUser userConnected,
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(defaultValue = "0") int page, Model model) {
		paramsBusqueda.put("estudianteId", userConnected.getId().toString());
		Paginacion<EstudianteCurso, EstudianteCursoDTO> paginacion = estudianteCursoService.listadoPaginado( getParams( page, 3), paramsToMap(paramsBusqueda));
	    model.addAttribute("paginacion", paginacion);
	    // Retorna la vista de cursos, pero solo el fragmento del bloque seleccionado
	    return "estudiante/home :: #bloqueMisCursos"; 
	}
	@GetMapping("/estudiante/cursos/ajax/cursosdisponibles")
	public String paginarCursosDisponiblesAJAX(			
			@AuthenticationPrincipal SecurityUser userConnected,
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(defaultValue = "0") int page, Model model) {
		List<Long> areasId= getAreasEstudiante( userConnected.getId() );
		Paginacion<Curso, CursoDTO> paginacion =cursoService.listadoCursosDisponiblesPorAreas(  getParams( page, 3), userConnected.getId(), areasId);
		model.addAttribute("paginacionCD", paginacion);
		return "estudiante/home :: #bloqueCursosDisponibles"; 
	}
	
	private List<Long> getAreasEstudiante(Long estudianteId){
		return estudianteService.findById(estudianteId)
				.getAreasInteres().stream()
				.map(a -> a.getId())
				.toList();
	}
	
}
