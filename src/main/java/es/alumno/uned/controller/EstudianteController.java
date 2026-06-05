package es.alumno.uned.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.dto.UserPasswordChangeDTO;
import es.alumno.uned.exception.UserConectedNotMachtException;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.service.AreaTematicaService;
import es.alumno.uned.service.EstudianteService;
import es.alumno.uned.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
/**
 * Clase controladora de operaciones de la entidad Estudiante.
 * 
 */
@Controller
@RequestMapping("/estudiante")
public class EstudianteController extends BaseCrudController {

	private Map<String, String> CONFIG_MODEL = Map.of(
	        "viewName", "estudiante/editar-perfil",
	        "url", "/registro",
	        "urlCancel", "/home",
	        "urlBack", "/"
	    );
    private final EstudianteService estudianteService;
    private final AreaTematicaService areaService;
    
    @Autowired 
    SmartValidator validator;
    
    public EstudianteController(EstudianteService estudianteService, UsuarioService usuarioService, 
    		AreaTematicaService areaService) {
        this.estudianteService = estudianteService;
        this.areaService= areaService;
    }
    /**
     * Edición del perfil propio de cada estudiante.
     * 
     * @param userConnected Usuario conectado.
     * @param model Modelo a visualizar.
     * @return Vista de los datos.
     */
    @GetMapping("/miperfil")
    public String perfil(@AuthenticationPrincipal SecurityUser userConnected, 
    		Model model) {

        EstudianteDTO dto = estudianteService.findById(userConnected.getId());
        setModeloFormulario(model, "estudiante/editar-perfil","/estudiante/miperfil","/");
        model.addAttribute("form", dto);
        return model.getAttribute("viewName").toString();
    }
	/**
	 * Solicitud de cambio de password.
	 * @param model Modelo a rellenar para la vista.
	 * @return Vista que dibuja los datos.
	 */
	@GetMapping("/miperfil/password/{id}")
	public String showPasswordForm(@AuthenticationPrincipal SecurityUser userConnected,
			@PathVariable Long id,
			Model model) {
		model.addAttribute("viewName", "comun/cambio-password");
		model.addAttribute("isUser", true);
	    model.addAttribute("form", new UserPasswordChangeDTO(userConnected.getUsername()));
	    return "comun/cambio-password";
	}

    /**
     * Edición de los datos de un Estudiante.
     * @param id Identificador del Estudiante.
     * @param userConnected  Usuario conectado.
     * @param model Modelo a mostrar.
     * @return Vista que muestra los datos.
     */
    @GetMapping("/editar/{id}")
    public String modificar(@PathVariable Long id,
    		@AuthenticationPrincipal SecurityUser userConnected, 
    		HttpServletRequest request,
    		Model model) {

    	if (userConnected.getRol().equals("ESTUD")) {
    		id = userConnected.getId();
    	}
    	Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
    	if (flashMap != null && flashMap.containsKey("success")) {
    	    model.addAttribute("success", flashMap.get("success"));
    	}

        EstudianteDTO dto = estudianteService.findById(id);
        setModeloFormulario(model,"estudiante/editar-perfil", "/estudiante/miperfil", "/");
        model.addAttribute("form", dto);
        model.addAttribute("areasDisponibles", areaService.listAll());
        model.addAttribute("isUser", true);
        return model.getAttribute("viewName").toString();
    }
    
    @PostMapping("/miperfil")
    public String actualizarPerfil(@AuthenticationPrincipal SecurityUser userConnected,
    		@RequestParam(required = false) List<Long> areasSeleccionadas,
            @Valid  @ModelAttribute("form") EstudianteDTO form,
            BindingResult result,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            Model model) {

    	preparaModeloPost(model, request, "/estudiante/miperfil");
    	if (userConnected.getRol().equals("ESTUD")) {
	    	if (!Objects.equals(userConnected.getId(),form.getId())){
	    		throw new UserConectedNotMachtException("error.403.title", form, "");
	    	}
    	}
        if (result.hasErrors()) {
        	return "estudiante/editar-perfil";
        }
        if (areasSeleccionadas != null) {
	        List<AreaTematicaDTO> nuevasAreas = areaService.findAllById(areasSeleccionadas);
	        form.setAreasInteres(nuevasAreas);
        }

        estudianteService.grabar(form, "");
        redirectAttributes.addFlashAttribute("success", "mensaje.grabacionOK");
        return "redirect:/estudiante/editar/" + form.getId().toString() + "?sucess";
    }
    /**
     * Listado genérico de Estudiantes para la consola de administración.
     * @param params Parámetros de búsqueda.
     * @param page Número de página.
     * @param model Modelo a rellenar.
     * @return Vista que muestra los datos.
     */
    @GetMapping({"","/"})
    public String lista(
    		@RequestParam Map<String, String> params,
    		@RequestParam(defaultValue = "0") int page, 
    		Model model) {
    	var paginacion = estudianteService.listadoPaginado(paramsToMap(params),  getParams(page));
 
		setModeloListado(model, "estudiante/estudiantes", 
				"/registro",
	    		"/estudiante/editar", 
	    		"/home");

    	model.addAttribute("paginacion", paginacion);
        return model.getAttribute("viewName").toString();
    }

	private void preparaModeloGet(Model modelo) {
		
		modelo.addAttribute("isUser", false);
		setModeloFormulario(modelo,CONFIG_MODEL.get("viewName"), 
	            CONFIG_MODEL.get("url"), 
	            CONFIG_MODEL.get("urlCancel"));
		
	}
	private void preparaModeloPost(Model modelo, HttpServletRequest request, String urlRequest) {
		
		preparaModeloGet(modelo);
		setRequestFormulario(request,CONFIG_MODEL.get("viewName"), 
	            CONFIG_MODEL.get("url"), 
	            CONFIG_MODEL.get("urlCancel"),
	            urlRequest);
	
	}

}

