package es.alumno.uned.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.exception.UserConectedNotMachtException;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.service.AreaTematicaService;
import es.alumno.uned.service.EstudianteService;
import es.alumno.uned.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

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

    @GetMapping("/miperfil")
    public String perfil(@AuthenticationPrincipal SecurityUser userConnected, 
    		Model model) {

        EstudianteDTO dto = estudianteService.findById(userConnected.getId());
        setModeloFormulario(model, "estudiante/editar-perfil","/estudiante/miperfil","/");
        model.addAttribute("form", dto);
        return model.getAttribute("viewName").toString();
    }

    @GetMapping("/editar/{id}")
    public String modificar(@PathVariable Long id,Principal principal, 
    		Model model) {

        EstudianteDTO dto = estudianteService.findById(id);
        setModeloFormulario(model,"estudiante/editar-perfil", "/registro", "/");
        model.addAttribute("form", dto);
        model.addAttribute("areasDisponibles", areaService.listAll());
        return model.getAttribute("viewName").toString();
    }
    
    @PostMapping("/miperfil")
    public String actualizarPerfil(@AuthenticationPrincipal SecurityUser userConnected,
    		@RequestParam(required = false) List<Long> areasSeleccionadas,
            @Valid  @ModelAttribute("form") EstudianteDTO form,
            BindingResult result,
            HttpServletRequest request,
            Model model) {

    	preparaModeloPost(model, request, "/estudiante/miperfil");
    	if (!Objects.equals(userConnected.getId(),form.getId())){
    		throw new UserConectedNotMachtException("error.403.title", form, "");
    	}
        if (result.hasErrors()) {
        	return "estudiante/editar-perfil";
        }
        if (areasSeleccionadas != null) {
	        List<AreaTematicaDTO> nuevasAreas = areaService.findAllById(areasSeleccionadas);
	        form.setAreasInteres(nuevasAreas);
        }

        estudianteService.grabar(form, "");
        model.addAttribute("sucess", "mensaje.grabacionOK");
        return "redirect:/" + request.getAttribute("urlRequest") + "?sucess";
    }
    /*^*
     * Listado genérico de Estudiantes para la consola de administración.
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

