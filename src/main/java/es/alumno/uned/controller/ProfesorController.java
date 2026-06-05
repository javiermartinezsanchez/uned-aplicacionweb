package es.alumno.uned.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.dto.EstudianteCursoDTO;
import es.alumno.uned.exception.CursoNotExistException;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.service.ContenidoExtraService;
import es.alumno.uned.service.CursoService;
import es.alumno.uned.service.EstudianteCursoService;
/**
 * Controlador principal acciones del profesor.
 */

@Controller
@RequestMapping("/profesor")
public class ProfesorController extends BaseCrudController {
	
	@Autowired
	CursoService cursoService;
	
	@Autowired
	EstudianteCursoService estudianteCursoService;
	
	@Autowired
	ContenidoExtraService contenidoExtraService;
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
		setModeloListado(model, "profesor/tareaspendientes" , 
	    		"",	"/profesor/calificar/", "/home");
		paramsBusqueda.put("responsableId", userConnected.getId().toString());
		var paginacion = estudianteCursoService.listadoTareasPendientes(getParams(page), paramsToMap(paramsBusqueda)); 
		model.addAttribute("paginacion", paginacion);
		return model.getAttribute("viewName").toString();
	}
	/**
	 * Se obtiene el listado de los cursos de un profesr.
	 * @param userConnected
	 * @param idCurso
	 * @param paramsBusqueda
	 * @param page
	 * @param model
	 * @return
	 */
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
		if (paginacion.getContenido().isEmpty())  {
			throw new CursoNotExistException("msg.exception.notfound", "curso.title");
		}
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
	/**
	 * VerEntrega de un trabajo de un curso.
	 * 
	 * @param estudianteId Identificador del estudiante que ha realizado la entrega.
	 * @param cursoId Identificador del curso.
	 * @param moduloId Identificador del Módulo que se ha entregado.
	 * @param userConnected Usuario conectado (Profesor)
	 * @return El documento para su revisión en una ventana nueva.
	 */
	@GetMapping("/{estudianteId}/curso/{cursoId}/modulo/{moduloId}/verEntrega")
	public ResponseEntity<?> descargarEntrega(
			@PathVariable Long estudianteId,
	        @PathVariable Long cursoId,
	        @PathVariable Long moduloId,
	        @AuthenticationPrincipal SecurityUser userConnected) {

	    	EstudianteCursoDTO ec = estudianteCursoService.getCursoModulo(userConnected.getId(), estudianteId, cursoId, moduloId);        
			if (ec == null) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No estás inscrito a este curso");
			}
		
			String nombreArchivo = ec.getModulos().get(0).getUrlEntrega();
//	    	String nombreArchivo = ec.getModulos().stream()
//	    						   .filter(m -> m.getModuloId().equals(moduloId))
//	    						   .filter(m -> m.getUrlEntrega() != null && !m.getUrlEntrega().isBlank())
//	    						   .map(m -> m.getUrlEntrega())
//	    						   .findFirst()
//	    						   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe entrega."));
				    						   
			Resource resource = contenidoExtraService.getResource(nombreArchivo);
			if (resource == null) {
				throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, "El archivo físico no se encuentra en el servidor");
			}
			
			return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType("application/pdf"))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nombreArchivo.substring(nombreArchivo.indexOf("_") + 1) + "\"")
	                .body(resource);
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
	/**
	 * Actualización de mis curso por llamada Ajax desde el paginador de la home.
	 * 
	 * @param userConnected Usuario Conectado (Profesor)
	 * @param paramsBusqueda Mapa de parámetros del formulario de búsqueda.
	 * @param page Número de página
	 * @param model mdelo a rellenar.
	 * @return Vista :: fragmento que se va a refrescar.
	 */
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
