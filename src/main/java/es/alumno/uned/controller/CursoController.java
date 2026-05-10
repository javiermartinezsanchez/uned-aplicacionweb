package es.alumno.uned.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.dto.ModuloDTO;
import es.alumno.uned.dto.ValoracionDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.util.ControllerUtil;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.service.AreaTematicaService;
import es.alumno.uned.service.CursoService;
import es.alumno.uned.service.ModuloService;
import es.alumno.uned.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
public class CursoController extends BaseCrudController {

	CursoService cursoService;
	AreaTematicaService areaTematicaService;
	UsuarioService usuarioService;
	ModuloService moduloService;
	public CursoController(CursoService cursoService, AreaTematicaService areaTematicaService,
			UsuarioService usuarioService, 
			ModuloService moduloService) {
		this.cursoService = cursoService;
		this.areaTematicaService = areaTematicaService;
		this.usuarioService = usuarioService;
		this.moduloService = moduloService;
	}
	/**
	 * Método para añadir un nuevo Curso.
	 * <p>Se invoca mediate GET y con el mapping "/curso/nuevo"
	 * <p>Se genera un nuevo {@link CursoDTO} que se envia a la vista.
	 * <p>Injectamos el Id del usuario para filtrarlo por "responsableId"
	 * @param userConnected Usuario Conectado para identificarlo.
	 * @param model {@link Model} Modelo de la vista.
	 * @return Vista que vamos a utilizar.
	 */
	@GetMapping("/curso/nuevo")
	public String nuevo(@AuthenticationPrincipal SecurityUser userConnected, 
			Model model) {
		setModeloFormulario(model, "curso/curso", "/curso/guardar", "/curso");
	    model.addAttribute("form", new CursoDTO(userConnected.getId()));
	    model.addAttribute("usuarios", usuarioService.listarProfesores());
	    return model.getAttribute("viewName").toString();
	}
	
	/**
	 * Método para guardar la información del Curso.
	 * <p>Se invoca mediate POST y con el mapping "/curso/guardar"
	 * @param userDetails Información del usuario "logado"
	 * @param form Datos introducidos en el formulario de la vista.
	 * @param result {@link BindingResult} de las validaciones de los campos.
	 * @param imagen Imagen del curso si existe.
	 * @param redirectAttributes Atributos del modelo no relacionados con el modelo.
	 * @param model {@link Model} Modelo completo enviado
	 * @return Redirigimos a la vista del Módulo correspondiente. 
	 */
	@PostMapping("/curso/guardar")
	public String guarda(@AuthenticationPrincipal UserDetails userDetails,
			@Valid @ModelAttribute("form") CursoDTO form,
	        BindingResult result,
	        MultipartFile imagen,
	        RedirectAttributes redirectAttributes, 
	        Model model) throws IOException {

	    if (result.hasErrors()) {
	    	prepararVistaEdicion(form, model);
	        model.addAttribute("usuarios", usuarioService.listarProfesores());
	        return "curso/curso";
	    }

	    var cursoGrabado = cursoService.grabar(form, imagen, userDetails.getUsername());
	    model.addAttribute("form", cursoGrabado);
	    redirectAttributes.addFlashAttribute("success", "mensaje.grabacionOK");
		return String.format("redirect:/curso/curso/%d", cursoGrabado.getId());
	}
	/**
	 * Para definir los módulos existentes y los disponibles en el modelo
	 * @param cursoDTO 
	 * @param model
	 */
	private void prepararVistaEdicion(CursoDTO cursoDTO, Model model) {
	    // Volvemos a buscar los módulos que NO están en el curso actual
	    List<ModuloDTO> todos = moduloService.listAll();
	    
	    // Filtramos para el select
	    List<ModuloDTO> disponibles = todos.stream()
	        .filter(m -> cursoDTO.getModulos().stream()
	            .noneMatch(rel -> rel.getModuloId().equals(m.getId())))
	        .toList();
	    
	    model.addAttribute("modulosDisponibles", disponibles);
	    
	    // 2. RECUPERAR DESCRIPCIONES (Nombres) de los módulos en la tabla
	    // Como el nombreModulo no viaja en el POST, lo re-asignamos buscando en la lista 'todos'
	    cursoDTO.getModulos().forEach(rel -> {
	        todos.stream()
	            .filter(m -> m.getId().equals(rel.getModuloId()))
	            .findFirst()
	            .ifPresent(m -> rel.setNombreModulo(m.getTitulo()));
	    });
	}
	@GetMapping("/curso/curso/{id}")
    public String modifica(@AuthenticationPrincipal SecurityUser userConnected, 
    		@PathVariable("id") Long id,
    		Model model) {
		var rol = userConnected.getRol();
		var curso = cursoService.getCurso(id);
		prepararVistaEdicion(curso,model);
		model.addAttribute("url", "/curso/guardar");
		model.addAttribute("urlCancel", "/curso");
		model.addAttribute("form", cursoService.getCurso(id));
	    model.addAttribute("usuarios", usuarioService.listarProfesores());
		return "curso/curso";
	}
	@GetMapping("/viewcurso/{id}")
    public String verFicha(Model model, 
    		@PathVariable("id") Long id) {
		model.addAttribute("url", "/curso/guardar");
		model.addAttribute("urlBack", "/");
		model.addAttribute("curso", cursoService.getCurso(id));
		return "curso/fichacurso";
	}

	/**
	 * Método de listado genérico de Cursos.
	 * <p>Se realiza la consulta con los parámetros (opcionales) del buscador.
	 * @param paramsBusqueda Mapa con los parámetros de búsqueda.
	 * @param page Número de página del listado (por defecto 0)
	 * @param model {@link Model} Modelo de la vista.
	 * @return Vista que vamos a utilizar
	 */
    @GetMapping("/curso")
    public String ListadoGeneral(@AuthenticationPrincipal SecurityUser userConnected,
    		@RequestParam Map<String, String> paramsBusqueda,
            @RequestParam(name="page", defaultValue = "0") int page,
            Model model) {
    	Pageable pageRequest= PageRequest.of(page, 10);
        var filtros = ControllerUtil.paramsToMap(paramsBusqueda);
       Paginacion<Curso, CursoDTO> paginacion = cursoService.listadoPaginado("/curso", pageRequest, filtros);
        model.addAttribute("url", "curso");
        model.addAttribute("urlAlta", "/curso/nuevo");
        model.addAttribute("urlBack", "/home");
        model.addAttribute("paginacion", paginacion);
        //model.addAttribute("areas", areaTematicaService.listAll());
        model.addAttribute("responsableId", userConnected.getId());
        model.addAttribute("responsables", usuarioService.listarProfesores());
        model.addAttribute("query", ControllerUtil.mapToQuery(filtros));
        model.addAttribute("paramsBusqueda",paramsBusqueda );
        return "curso/cursos"; 
    	
    }
    /**
     * Método que recibe la valoración de los cursos.
     * <p>Se envia un JSON en el body de la petición.
     * 
     * @param datos JSON con la estructura: 
     * <pre>{ idElemento : (curso_id),
     *  valoracion : (valoración enviada 1-5)
     *}</pre>
     * @return
     */
	@PostMapping("/valoracionCurso")
    @ResponseBody // CRUCIAL: Indica que el retorno es JSON, no una vista HTML
    public ResponseEntity<Map<String, Object>> guardarValoracion(
    		@AuthenticationPrincipal UserDetails userDetails,
    		@RequestBody ValoracionDTO datos) {
        
        // 1. Validaciones
        if (datos.getValoracion() == null || datos.getValoracion() < 1 || datos.getValoracion() > 5) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("mensaje", "La valoración debe ser un número entre 1 y 5.");
            return ResponseEntity.badRequest().body(error);
        }

        // 2. Guardamos valoración y devolvemos nuevo cálculo de media.
        BigDecimal mediaValoracion = cursoService.guardarValoracion(datos.getIdElemento(), datos.getValoracion(), userDetails.getUsername());
        // 3. Construcción de la respuesta JSON
        Map<String, Object> respuesta = new HashMap<>();
        
            respuesta.put("success", true);
            respuesta.put("mensaje", "¡Gracias por tu valoración!");
            // Opcional: devolver la nueva valoración promedio si quieres actualizarla en el frontend
            respuesta.put("nuevaPromedio", mediaValoracion); 
            return ResponseEntity.ok(respuesta);
    }
}
