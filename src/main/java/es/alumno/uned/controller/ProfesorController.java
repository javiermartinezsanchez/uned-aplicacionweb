package es.alumno.uned.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.dto.EstudianteCursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.service.CursoService;
import es.alumno.uned.service.EstudianteCursoService;
import jakarta.validation.Valid;
@Controller
@RequestMapping("/profesor")
public class ProfesorController extends BaseCrudController {
	
	@Autowired
	CursoService cursoService;
	
	@Autowired
	EstudianteCursoService estudianteCursoService;
	/**
	 * Home principal del profesor
	 * @param userConnected Usuario Conectado
	 * @param paramsBusqueda Parámetros de búsqueda
	 * @param page Número de página (para el slide de cursos)
	 * @param modelo Model a rellenar
	 * @return la vista que muestra el modelo
	 */
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
	/**
	 * Devolvemos la vista de Tareas Pendientes del Profesor
	 * @param userConnected Usuario Conectado (el profesor)
	 * @param paramsBusqueda Mapa de parámetros
	 * @param page Número de página
	 * @param model Modelo a enviar  a la vista
	 * @return Vista a visualizar
	 */
	@GetMapping("/pendientes")
	public String listaTareasPendientes(
			@AuthenticationPrincipal SecurityUser userConnected,
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(defaultValue = "0") int page, Model model) {
		setModeloListado(model, "profesor/tareasPendientes" , 
	    		"",	"/profesor/calificar/", "/home");
		paramsBusqueda.put("responsableId", userConnected.getId().toString());
		var paginacion = estudianteCursoService.listadoTareasPendientes(getParams(page), paramsToMap(paramsBusqueda)); 
		model.addAttribute("paginacion", paginacion);
		return model.getAttribute("viewName").toString();
	}
	@GetMapping("/seguimientocurso/{idCurso}")
	public String seguimientoCurso(
			@AuthenticationPrincipal SecurityUser userConnected,
			@PathVariable("idCurso") Long idCurso,
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(defaultValue = "0") int page, Model model
			) {
		setModeloListado(model, "profesor/seguimientocurso" , 
	    		"",	"/profesor/calificar/", "/home");
		paramsBusqueda.put("curso.usuario.id", userConnected.getId().toString());
		paramsBusqueda.put("curso.id", idCurso.toString());
		var paginacion = estudianteCursoService.listadoPaginado(getParams(page), paramsToMap(paramsBusqueda));
		model.addAttribute("isProfesor", true);
		model.addAttribute("curso", paginacion.getContenido().get(0));
		model.addAttribute("paginacion", paginacion);
		return model.getAttribute("viewName").toString();
	}
	/**
	 * Calificamos una tarea.
	 * <p>Se captura el id del usuario de SecurityUser.
	 * <p>Si el curso no corresponde al usuario generará una excepción.
	 * 
	 * @param userConnected Usuario conectado (profesor)
	 * @param idEstudiante Identificador del Estudiante
	 * @param idCurso Identificador del curso
	 * @param idModulo Identificador del módulo que se ha entregado.
	 * @param model Modelo a rellenar.
	 * @return Vista a mostrar.
	 */
	@GetMapping("/calificar/{idEstudiante}/{idCurso}/{idModulo}")
	public String calificarEntrega(@AuthenticationPrincipal SecurityUser userConnected,
			@PathVariable("idEstudiante") Long idEstudiante,
			@PathVariable("idCurso") Long idCurso,
			@PathVariable("idModulo") Long idModulo,
			Model model
			) {
		
			var ec = estudianteCursoService.getCursoModulo(userConnected.getId(),
					idEstudiante, idCurso, idModulo);
			setModeloFormulario(model, "profesor/revisartrabajo","/profesor/cursorevisado", "/profesor/pendientes");
			model.addAttribute("curso", ec);
			model.addAttribute("isProfesor", true);
		return model.getAttribute("viewName").toString();
	}
	
	@PostMapping("/cursorevisado")
	public String grabarRevision(
			@AuthenticationPrincipal SecurityUser userConnected,
			@ModelAttribute("form") EstudianteCursoDTO form,
			RedirectAttributes redirectAttributes, 
	        Model model) {
			estudianteCursoService.calificarModulo(form.getModulos().get(0));
		return "redirect:/profesor/pendientes";
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
