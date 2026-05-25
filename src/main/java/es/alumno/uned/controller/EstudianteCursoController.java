package es.alumno.uned.controller;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.dto.EstudianteCursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.EstadoCursoModulo;
import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.entities.TipoModulo;
import es.alumno.uned.model.records.FicheroData;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.service.CursoService;
import es.alumno.uned.service.EstudianteCursoService;
import es.alumno.uned.service.EstudianteService;
@RequestMapping("/estudiante")
@Controller
public class EstudianteCursoController extends BaseCrudController {

	@Autowired
	EstudianteCursoService estudianteCursoService;
	@Autowired
	CursoService cursoService;
	@Autowired
	EstudianteService estudianteService;
	
	
	@GetMapping("/home")
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

	@GetMapping("/subscribircurso/{id}")
	public String subscribirCurso(@PathVariable Long id,
							     @AuthenticationPrincipal SecurityUser userConnected,
	                              RedirectAttributes redirectAttributes) {

	        
	        estudianteCursoService.subscribirAlumnoACurso(userConnected.getId(), id);
	        redirectAttributes.addFlashAttribute("success",
	                "Te has suscrito correctamente al curso.");

	    	//redirectAttributes.addFlashAttribute("error", e.getMessage());

	        //redirectAttributes.addFlashAttribute("error",
	        //        "No se pudo completar la suscripción.");

	    return "redirect:/estudiante/home";
	}

	@GetMapping("/micurso/{idCurso}")
	public String verMiCurso(@PathVariable("idCurso") Long idCurso, 
            @AuthenticationPrincipal SecurityUser userConnected,
            Model model) {
		
		model.addAttribute("curso", estudianteCursoService.getCurso(userConnected.getId(), idCurso));
		return "estudiante/cursoEstudiante";
		
	}
	@PostMapping("/modulo/completar")
	public String completarModuloManual(MultipartFile ficheroTarea,
										@RequestParam("idCurso") Long idCurso,
	                                    @RequestParam("idModulo") Long idModulo,
	                                    @RequestParam("tipoModulo") TipoModulo tipoModulo,
	                                    @AuthenticationPrincipal SecurityUser userConnected,
	                                    RedirectAttributes redirectAttributes) {
	    try {
	    	
	    	
	    	FicheroData docEntregaData = null;
			if ((ficheroTarea != null) && (!ficheroTarea.isEmpty())) {
				docEntregaData = new FicheroData(0,
						ficheroTarea.getOriginalFilename(),
						ficheroTarea.getContentType(),
						ficheroTarea.getBytes());
			}
	        estudianteCursoService.completarModulo(userConnected.getId(), idCurso, idModulo,tipoModulo, docEntregaData);
	        redirectAttributes.addFlashAttribute("mensajeExito", "¡Módulo completado con éxito! Se ha desbloqueado el siguiente contenido.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("mensajeError", "Error al completar el módulo: " + e.getMessage());
	    }
	    // 3. Redirigimos de vuelta a la ficha de seguimiento del curso del estudiante
	    return "redirect:/estudiante/micurso/" + idCurso;
	}
	/**
	 * Obtención del certificado del curso.
	 * 
	 * @param idCurso Identificador del curso
	 * @param userConnected Usuario Conectado
	 * @param model Modelo con los datos visualizables.
	 * @return La vista que nos muestra el Certificado.
	 */
	@GetMapping("/curso/certificado/{idCurso}")
	public String certificadoCurso(@PathVariable("idCurso") Long idCurso, 
    @AuthenticationPrincipal SecurityUser userConnected,
    Model model) {
		
		EstudianteCursoDTO ec = estudianteCursoService.getCurso(userConnected.getId(), idCurso);
		if (ec == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No estás inscrito a este curso");
		}
		if (ec.getEstado() != EstadoCursoModulo.COMPLETADO) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El diploma aún no está disponible.");
			
		}
		DateTimeFormatter formateador = DateTimeFormatter
	            .ofLocalizedDate(FormatStyle.LONG)
	            .withLocale(LocaleContextHolder.getLocale());

		model.addAttribute("nombreEstudiante", ec.getEstudiante().getNombreCompleto());
		model.addAttribute("email", ec.getEstudiante().getEmail());
		model.addAttribute("tituloCurso", ec.getCurso().getTitulo());
		model.addAttribute("fechaFinalizacion", ec.getFechaCompletado().format(formateador));
		return "estudiante/certificadocurso";
	}
	@GetMapping("/cursos/ajax/miscursos")
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
	
	@GetMapping("/cursos/ajax/cursosdisponibles")
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
