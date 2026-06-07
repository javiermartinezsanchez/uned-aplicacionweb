package es.alumno.uned.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.dto.ContenidoExtraDTO;
import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.dto.CursoModuloDTO;
import es.alumno.uned.dto.ModuloDTO;
import es.alumno.uned.dto.ValoracionDTO;
import es.alumno.uned.exception.FileSizeExcedeedException;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.records.FicheroData;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.service.AreaTematicaService;
import es.alumno.uned.service.CursoService;
import es.alumno.uned.service.ModuloService;
import es.alumno.uned.service.UsuarioService;
import jakarta.validation.Valid;
import tools.jackson.databind.ObjectMapper;
/**
 * Controlador de Curso
 * 
 * <p>Se definen la operaciones básicas sobre {@link Curso}
 * 
 */
@Controller
public class CursoController extends BaseCrudController {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CursoController.class);
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
		
		String urlBack = getUrlBack("/curso",userConnected.getRol()) ;
		setModeloFormulario(model, "curso/curso", "/curso/guardar", urlBack);
		var curso = new CursoDTO(userConnected.getId());
	    prepararVistaEdicion(curso,model);
	    model.addAttribute("curso", curso);
	    model.addAttribute("usuarios", usuarioService.listarProfesores());
	   
	    return model.getAttribute("viewName").toString();
	}
	
	/**
	 * Método para guardar la información del Curso.
	 * <p>Se invoca mediate POST y con el mapping "/curso/guardar"
	 * <p>Por configuración de Tomcat en el contenedor, se ha "empaquetado" la lista de módulos y de
	 * contenidos extra en un JSON (problema de enviar + de 50 campos en un formulario).
	 * @param userDetails Información del usuario "logado"
	 * @param form Datos introducidos en el formulario de la vista.
	 * @param result {@link BindingResult} de las validaciones de los campos.
	 * @param contenidoExtrasFile {@link MultipartHttpServletRequest} de los ficheros de contenidos extra, si los hubiera.
	 * @param redirectAttributes Atributos del modelo no relacionados con el modelo.
	 * @param model {@link Model} Modelo completo enviado
	 * @return Redirigimos a la vista del Módulo correspondiente. 
	 */
	@PostMapping("/curso/guardar")
	public String guarda(@AuthenticationPrincipal UserDetails userDetails,
			@Valid @ModelAttribute("curso") CursoDTO form,
	        BindingResult result,
	        @RequestParam(value = "jsonModulos", required = false) String jsonModulos,
	        @RequestParam(value = "jsonContenidos", required = false) String jsonContenidos, 
	        MultipartHttpServletRequest contenidoExtrasFile,
	        RedirectAttributes redirectAttributes, 
	        Model model) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();

	    // Reconstruimos la lista de Módulos en el DTO principal desde el json
	    if (jsonModulos != null && !jsonModulos.isEmpty()) {
	        try {
	        	CursoModuloDTO[] arrayModulos = objectMapper.readValue(jsonModulos, CursoModuloDTO[].class);
	            form.setModulos(Arrays.asList(arrayModulos));
	        } catch (Exception e) { log.error("Error Módulos: " + e.getMessage()); }
	    }
	 // Reconstruimos la lista de contenidosExtra en el DTO principal desde el json
	    if (jsonContenidos != null && !jsonContenidos.isEmpty()) {
	        try {
	            ContenidoExtraDTO[] arrayContenidos = objectMapper.readValue(jsonContenidos, ContenidoExtraDTO[].class);
	            form.setContenidosExtra(Arrays.asList(arrayContenidos));
	        } catch (Exception e) { log.error("Error Contenidos: " + e.getMessage()); }
	    }
	    
	    MultipartFile imagen = contenidoExtrasFile.getFile("imagen");
		FicheroData imagenData = null;
		if (!imagen.isEmpty()) {
			imagenData = new FicheroData(0,
					imagen.getOriginalFilename(),
					imagen.getContentType(),
					imagen.getBytes());
		}
		log.info("después de la imagen");
	    if (result.hasErrors()) {
	    	log.info("tiene errores");
	    	log.info("errores: " + result.toString());
	    	prepararVistaEdicion(form, model);
	        model.addAttribute("usuarios", usuarioService.listarProfesores());
	        return "curso/curso";
	    }
	    log.info("inicio de lectura de archivos extra");
	    List<FicheroData> archivosExtra = contenidoExtrasFile.getFileMap()
	    		.entrySet()
	    		.stream()
	    	    .filter(entry -> !entry.getValue().isEmpty() && entry.getKey().contains("_")) 
	    	    .map(entry -> {
	    	        try {
	    	            MultipartFile file = entry.getValue();
	    	            if (file.getSize() > 1048576) {
	    	                throw new FileSizeExcedeedException("error.archivo.demasiado_grande", form);
	    	            }
	    	            // Extraemos el índice del nombre del campo 
	    	            int index = Integer.parseInt(entry.getKey().split("_")[1]);
	    	            return new FicheroData(
    	            		index,
	    	                file.getOriginalFilename(),
	    	                file.getContentType(),
	    	                file.getBytes()
	    	                
	    	            );
	    	        } catch (IOException e) {
	    	            throw new RuntimeException("Error al leer bytes del archivo", e);
	    	        }
	    	    })
	    	    .toList();
	    log.debug("FIN de lectura de archivos extra");
	    log.debug("LLAMA A cursoService.grabar");
	    var cursoGrabado = cursoService.grabar(form, imagenData,archivosExtra,  userDetails.getUsername());
	    log.debug("DESPUÉS DE cursoService.grabar");
	    model.addAttribute("curso", cursoGrabado);
	    redirectAttributes.addFlashAttribute("success", "mensaje.grabacionOK");
	    log.debug("REDIRIGIMOS A: " + String.format("redirect:/curso/curso/%d", cursoGrabado.getId()));
		return String.format("redirect:/curso/curso/%d?success=true", cursoGrabado.getId());
	}
	/**
	 * Para definir los módulos existentes y los disponibles en el modelo.
	 * <ol>
	 * <li>Se seleccionan todos los módulos existentes</li>
	 * <li>Se filtran los ya existentes (en edición) para asignarle los títulos.</li>
	 * </ol>
	 * @param cursoDTO Curso que estamos insertando/editando
	 * @param model Modelo para añadir los atributos.
	 */
	private void prepararVistaEdicion(CursoDTO cursoDTO, Model model) {
	    // Volvemos a buscar los módulos que NO están en el curso actual
	    List<ModuloDTO> todos =  new ArrayList<>(moduloService.listAll());
	    todos.sort(Comparator.comparing(ModuloDTO::getTitulo));
	    
	    if (cursoDTO.getModulos() != null) {
	    cursoDTO.getModulos().forEach(rel -> {
	        todos.stream()
	            .filter(m -> m.getId().equals(rel.getModuloId()))
	            .findFirst()
	            .ifPresent(m -> rel.setNombreModulo(m.getTitulo()));
	        });
	    }
	    model.addAttribute("modulosDisponibles", todos);
	    
	}
	/**
	 * Consulta individual del Curso.
	 * @param userConnected Usuario Conectado para determinar la dirección de 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/curso/curso/{id}")
    public String modifica(@AuthenticationPrincipal SecurityUser userConnected, 
    		@PathVariable("id") Long id,
    		@RequestParam(value = "success", required = false) String success,
    		Model model) {
		var curso = cursoService.getCurso(id);
		prepararVistaEdicion(curso,model);
		if (success != null) {
	        model.addAttribute("success", "mensaje.grabacionOK");
	    }
		model.addAttribute("url", "/curso/guardar");
		model.addAttribute("urlCancel", getUrlBack("/curso",userConnected.getRol()));
	    if (!model.containsAttribute("curso")) {
	    	model.addAttribute("curso", cursoService.getCurso(id));
	    }
		
	    model.addAttribute("usuarios", usuarioService.listarProfesores());
		return "curso/curso";
	}
	/**
	 * Método público de vista de la ficha de un curso.
	 * @param id Id del Curso a visualizar.
	 * @param model Modelo a construir.
	 * @return Vista del Curso.
	 */
	@GetMapping("/viewcurso/{id}")
    public String verFicha(@AuthenticationPrincipal SecurityUser userConnected, 
    		@PathVariable("id") Long id,
    		Model model) {
		model.addAttribute("url", "/curso/guardar");
		model.addAttribute("urlBack", "/");
		if ((userConnected == null) || (userConnected.getRol() == "ESTUD")) {
			model.addAttribute("curso", cursoService.getCurso(id, "visita"));
		}
		else {
			model.addAttribute("curso", cursoService.getCurso(id));
		}
		return "curso/fichacurso";
	}

	/**
	 * Método de listado genérico de Cursos.
	 * <p>Se realiza la consulta con los parámetros (opcionales) del buscador.
	 * @param urlBase Se extrae del path del mapping, para identificar al profesor o al administrador.
	 * @param paramsBusqueda Mapa con los parámetros de búsqueda.
	 * @param page Número de página del listado (por defecto 0)
	 * @param model {@link Model} Modelo de la vista.
	 * @return Vista que vamos a utilizar
	 */
    @GetMapping("/{urlBase}/curso")
    @PreAuthorize(
    	    "(#urlBase == 'admin' and hasRole('ADMIN')) or " +
    	    "(#urlBase == 'profesor' and hasRole('PROFE'))"
    	)
    public String ListadoGeneral(@AuthenticationPrincipal SecurityUser userConnected,
    		@PathVariable("urlBase") String urlBase,
    		@RequestParam Map<String, String> paramsBusqueda,
            @RequestParam(name="page", defaultValue = "0") int page,
            Model model) {
    	if (urlBase.equals("profesor")){
    		paramsBusqueda.put("responsableId", userConnected.getId().toString());
    	}
    	Paginacion<Curso, CursoDTO> paginacion = cursoService.listadoPaginado(getParams(page), paramsToMap(paramsBusqueda));
    	setModeloListado(model, "curso/cursos", "/curso/nuevo", "curso", "/home");
        model.addAttribute("paginacion", paginacion);
        model.addAttribute("responsableId", userConnected.getId());
        model.addAttribute("responsables", usuarioService.listarProfesores());
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
    @ResponseBody 
    public ResponseEntity<Map<String, Object>> guardarValoracion(
    		@AuthenticationPrincipal UserDetails userDetails,
    		@RequestBody ValoracionDTO datos) {
        
        // 1. Validaciones
        if (datos.getValoracion() == null || datos.getValoracion() < 1 || datos.getValoracion() > 5) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("mensaje", getMessage("error.valoracion.rango")); //"La valoración debe ser un número entre 1 y 5.");
            return ResponseEntity.badRequest().body(error);
        }

        BigDecimal mediaValoracion = cursoService.guardarValoracion(datos.getIdElemento(), datos.getValoracion(), userDetails.getUsername());
        Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("success", true);
            respuesta.put("mensaje", getMessage("curso.valoracion.exito")); //"¡Gracias por tu valoración!");
            respuesta.put("nuevaPromedio", mediaValoracion); 
            return ResponseEntity.ok(respuesta);
    }
	/**
	 * Método para obtener de forma paginada los curos "Más visitados".
	 * <p>Se realiza la búsqueda y se injecta en el fragmento de la vista para refrescar los cursos.
	 * @param paramsBusqueda Parámetros opcionales para la búsqueda.
	 * @param page Número de página de datos a devolver.
	 * @param model Modelo de la vista.
	 * @return La vista y el fragmento en el que se va a insertar el resultado.
	 */
	@GetMapping("/cursos/ajax/masvisitados")
	public String paginarDestacadosAJAX(
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(defaultValue = "0") int page, Model model) {

		Paginacion<Curso, CursoDTO> paginacion = cursoService.listadoOrderByNumVisistas( getParams( page, 3), paramsToMap(paramsBusqueda));
	    model.addAttribute("paginacion", paginacion);
	    return "home :: #bloqueMasVisitados"; 
	}
	/**
	 * Método para obtener de forma paginada los curos ordenados por su valoración de forma descendente.
	 * <p>Se realiza la búsqueda y se injecta en el fragmento de la vista para refrescar los cursos.
	 * @param paramsBusqueda Parámetros opcionales para la búsqueda.
	 * @param page Número de página de datos a devolver.
	 * @param model Modelo de la vista.
	 * @return La vista y el fragmento en el que se va a insertar el resultado.
	 */
	@GetMapping("/cursos/ajax/masvalorados")
	public String paginarValoradosAJAX(
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(defaultValue = "0") int page, Model model) {

		Paginacion<Curso, CursoDTO> paginacion = cursoService.listadoOrderByValoracion( getParams( page, 3), paramsToMap(paramsBusqueda));
	    model.addAttribute("paginacionMV", paginacion);
	    // Retorna la vista de cursos, pero solo el fragmento del bloque seleccionado
	    return "home :: #bloqueMasValorados"; 
	}
	/**
	 * Método para obtener de forma paginada los cursos ordenados por el número de personas inscritas descendente.
	 * <p>Se realiza la búsqueda y se injecta en el fragmento de la vista para refrescar los cursos.
	 * @param paramsBusqueda Parámetros opcionales para la búsqueda.
	 * @param page Número de página de datos a devolver.
	 * @param model Modelo de la vista.
	 * @return La vista y el fragmento en el que se va a insertar el resultado.
	 */
	@GetMapping("/cursos/ajax/massubscritos")
	public String paginarMasInscritosAJAX(
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(defaultValue = "0") int page, Model model) {

		Paginacion<Curso, CursoDTO> paginacion = cursoService.listadoOrderByInscritos( getParams( page, 3), paramsToMap(paramsBusqueda));
	    model.addAttribute("paginacionME", paginacion);
	    // Retorna la vista de cursos, pero solo el fragmento del bloque seleccionado
	    return "home :: #bloqueMasSubscritos"; 
	}


}
