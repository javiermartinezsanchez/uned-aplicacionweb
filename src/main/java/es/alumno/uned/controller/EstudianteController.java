package es.alumno.uned.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class EstudianteController extends BaseCrudController {

	private Map<String, String> CONFIG_MODEL = Map.of(
	        "viewName", "estudiante/editar-perfil",
	        "url", "/registro",
	        "urlCancel", "/home",
	        "urlBack", "/",
	        "query", ""
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

    @GetMapping("/registro")
    public String nuevo(Model model) {
    	preparaModeloGet(model);
        model.addAttribute("form", new EstudianteDTO("ESTUD"));
        model.addAttribute("areasDisponibles", areaService.listAll());
        return model.getAttribute("viewName").toString();
    }
    @PostMapping("/registro")
    public String registrar(@AuthenticationPrincipal UserDetails usuario,
    		@RequestParam(required = false) List<Long> areasSeleccionadas,
    		@ModelAttribute("form") EstudianteDTO form,
            BindingResult result,
            HttpServletRequest request,
            Model model) {
    	preparaModeloPost(model, request);
    	validator.validate(form, result, Default.class); 
        if (result.hasErrors()) {
            return model.getAttribute("viewName").toString();
        }
        String usuarioAlta;
        if (usuario == null) {
        	usuarioAlta = form.getEmail();
        }
        else {
        	usuarioAlta = usuario.getUsername();
        }
        if (areasSeleccionadas != null) {
	        List<AreaTematicaDTO> nuevasAreas = areaService.findAllById(areasSeleccionadas);
	        form.setAreasInteres(nuevasAreas);
        }
        estudianteService.grabar(form, usuarioAlta);
        model.addAttribute("success", "mensaje.grabacionOK");
        return "redirect:/login?sucess";
    }
    
    @GetMapping({"/estudiante/miperfil"})
    public String perfil(@AuthenticationPrincipal SecurityUser userConnected, 
    		Model model) {

        EstudianteDTO dto = estudianteService.findById(userConnected.getId());
        setModeloFormulario(model, "estudiante/editar-perfil","/estudiante/miperfil","/");
        model.addAttribute("form", dto);
        model.addAttribute("areasDisponibles", areaService.listAll());
        return model.getAttribute("viewName").toString();
    }

    @GetMapping("/estudiante/editar/{id}")
    public String modificar(@PathVariable Long id,Principal principal, 
    		Model model) {

        EstudianteDTO dto = estudianteService.findById(id);
        setModeloFormulario(model,"estudiante/editar-perfil", "/registro", "/");
        model.addAttribute("form", dto);
        model.addAttribute("areasDisponibles", areaService.listAll());
        return model.getAttribute("viewName").toString();
    }
    
    @PostMapping("/estudiante/miperfil")
    public String actualizarPerfil(@AuthenticationPrincipal SecurityUser userConnected,
            @Valid  @ModelAttribute("form") EstudianteDTO form,
            BindingResult result,
            HttpServletRequest request,
            Model model) {

    	preparaModeloPost(model, request);
    	if (userConnected.getId() != form.getId()){
    		throw new UserConectedNotMachtException("error.403.title", form, "");
    	}
        if (result.hasErrors()) {
        	return "estudiante/editar-perfil";
        }
        estudianteService.grabar(form, "");
        model.addAttribute("sucess", "mensaje.grabacionOK");
        return "redirect:/estudiante/miperfil?sucess";
    }
    @GetMapping("/estudiante")
    public String lista(@RequestParam(defaultValue = "0") int page, 
    		Model model) {
    	Pageable pageRequest = PageRequest.of(page, 10);
    	var paginacion = estudianteService.listadoPaginado("/estudiante", pageRequest);
    	model.addAttribute("paginacion", paginacion);
    	 
        model.addAttribute("urlAlta", "/registro");
		model.addAttribute("urlBack", "/home");
	   
	    model.addAttribute("query","");
        return "estudiante/estudiantes";
    }

	private void preparaModeloGet(Model modelo) {
		
		modelo.addAttribute("isUser", false);
		setModeloFormulario(modelo,CONFIG_MODEL.get("viewName"), 
	            CONFIG_MODEL.get("url"), 
	            CONFIG_MODEL.get("urlCancel"));
		
	}
	private void preparaModeloPost(Model modelo, HttpServletRequest request) {
		
		preparaModeloGet(modelo);
		setRequestFormulario(request,CONFIG_MODEL.get("viewName"), 
	            CONFIG_MODEL.get("url"), 
	            CONFIG_MODEL.get("urlCancel"));
	
	}

}

