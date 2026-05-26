package es.alumno.uned.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.service.CursoService;
import es.alumno.uned.service.EstudianteCursoService;
@Controller
@RequestMapping("/profesor")
public class ProfesorController extends BaseCrudController {
	
	@Autowired
	CursoService cursoService;
	
	@Autowired
	EstudianteCursoService estudianteCursoService;
	
	@GetMapping("/home")
	public String profeHome(@AuthenticationPrincipal SecurityUser userConnected,
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(name="page", defaultValue = "0") int page,
			Model modelo) {
		paramsBusqueda.put("responsableId", userConnected.getId().toString());
		Paginacion<Curso, CursoDTO> paginacion = cursoService.listadoPaginado( getParams( page, 3), paramsToMap(paramsBusqueda));
		modelo.addAttribute("paginacion", paginacion);
		modelo.addAttribute("isProfesor", true);
		modelo.addAttribute("trabajosPendientes", estudianteCursoService.getTareasPendientes(userConnected.getId()));
		return "profesor/home";
	}
	@GetMapping("/pendientes")
	public String listaTareasPendientes(
			@AuthenticationPrincipal SecurityUser userConnected,
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(defaultValue = "0") int page, Model model) {
		
		paramsBusqueda.put("responsableId", userConnected.getId().toString());
		var paginacion = estudianteCursoService.listadoTareasPendientes(getParams(page), paramsToMap(paramsBusqueda)); 
		model.addAttribute("paginacion", paginacion);
		return "profesor/tareasPendientes";
	}
	@GetMapping("/cursos/ajax/miscursos")
	public String paginarMisCursosAJAX(
			@AuthenticationPrincipal SecurityUser userConnected,
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(defaultValue = "0") int page, Model model) {
		paramsBusqueda.put("responsableId", userConnected.getId().toString());
		Paginacion<Curso, CursoDTO> paginacion = cursoService.listadoPaginado( getParams( page, 3), paramsToMap(paramsBusqueda));
	    model.addAttribute("paginacion", paginacion);
	    // Retorna la vista de cursos, pero solo el fragmento del bloque seleccionado
	    model.addAttribute("isProfesor", true);
	    return "profesor/home :: #bloqueMisCursos"; 
	}

}
