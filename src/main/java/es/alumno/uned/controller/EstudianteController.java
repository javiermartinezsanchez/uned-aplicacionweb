package es.alumno.uned.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.service.AreaTematicaService;
import es.alumno.uned.service.EstudianteService;
import es.alumno.uned.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
public class EstudianteController extends BaseCrudController {

    private final EstudianteService estudianteService;
    private final UsuarioService usuarioService;
    private final AreaTematicaService areaService;
    
    public EstudianteController(EstudianteService estudianteService, UsuarioService usuarioService, 
    		AreaTematicaService areaService) {
        this.estudianteService = estudianteService;
        this.usuarioService = usuarioService;
        this.areaService= areaService;
    }

    @GetMapping("/registro")
    public String nuevo(Model model) {
    	preparaRegistro(model);
        model.addAttribute("form", new EstudianteDTO("ESTUD"));
        model.addAttribute("areasDisponibles", areaService.listAll());
        return model.getAttribute("viewName").toString();
    }
    @PostMapping("/registro")
    public String registrar(@AuthenticationPrincipal UserDetails usuario,
    		@RequestParam("areasSeleccionadas") List<Long> areasIds,
            @Valid @ModelAttribute("form") EstudianteDTO form,
           
            BindingResult result,
            Model model) {
    	preparaRegistro(model);
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
        List<AreaTematicaDTO> nuevasAreas = areaService.findAllById(areasIds);
        form.setAreasInteres(nuevasAreas);
        estudianteService.grabar(form, usuarioAlta);

        return "redirect:/estudiante/editar-perfil?sucess";
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
    public String actualizarPerfil(
            @Valid @ModelAttribute("form") EstudianteDTO form,
            BindingResult result,
            Principal principal,
            Model model) {
    	if (principal.getName() != form.getEmail()){
    		result.rejectValue("errorGlobal",  "error.en properties", "SÓLO SE PUEDE MODIFICAR EL PERFIL POR EL PROPIO USUARIO");
    	}
        if (result.hasErrors()) {
        	model.addAttribute("url", "/estudiante/miperfil");
            return "estudiante/editar-perfil";
        }

        estudianteService.grabar(form, "");
        return "redirect:/estudiante/miperfil?exito";
    }
    @GetMapping("/estudiante")
    public String lista(@RequestParam(name="page", defaultValue = "0") int page, 
    		Model model) {
    	Pageable pageRequest = PageRequest.of(page, 10);
    	var paginacion = estudianteService.listadoPaginado("/estudiante", pageRequest);
        model.addAttribute("urlAlta", "/registro");
		model.addAttribute("urlBack", "/home");
	    model.addAttribute("paginacion", paginacion);
	    model.addAttribute("query","");
        return "estudiante/estudiantes";
    }

	private void preparaRegistro(Model modelo) {
		setModeloFormulario(modelo,"estudiante/editar-perfil","/registro","/home");
		
	}
}

