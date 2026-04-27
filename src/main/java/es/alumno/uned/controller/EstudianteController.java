package es.alumno.uned.controller;

import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.mapper.RegistroEstudianteMapper;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.service.EstudianteService;
import es.alumno.uned.service.UsuarioService;
import jakarta.validation.Valid;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final UsuarioService usuarioService;

    public EstudianteController(EstudianteService estudianteService, UsuarioService usuarioService) {
        this.estudianteService = estudianteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
    	model.addAttribute("url", "/registro");
    	model.addAttribute("urlCancel", "/home");
        model.addAttribute("form", new EstudianteDTO("ESTUD"));
        return "estudiante/editar-perfil";
    }
    @GetMapping("/estudiantes/miperfil")
    public String editarPerfil(Principal principal, 
    		Model model) {

        EstudianteDTO dto = estudianteService.findById(
        		usuarioService.getIdByEmail(principal.getName()));
        
    	model.addAttribute("url", "/estudiantes/miperfil");
       	model.addAttribute("urlCancel", "/");
        model.addAttribute("form", dto);
        return "estudiante/editar-perfil";
    }

    @GetMapping("/estudiantes/estudiante/{id}")
    public String modificarPerfil(@PathVariable Long id,Principal principal, 
    		Model model) {

        EstudianteDTO dto = estudianteService.findById(id);
        
    	model.addAttribute("url", String.format("/estudiantes/estudiante/%d", id));
       	model.addAttribute("urlCancel", "/");
        model.addAttribute("form", dto);
        return "estudiante/editar-perfil";
    }

    @PostMapping("/registro")
    public String registrar(@AuthenticationPrincipal UserDetails usuario,
            @Valid @ModelAttribute("form") EstudianteDTO form,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("url", "/registro");
        	model.addAttribute("urlCancel", "/home");
            return "estudiante/editar-perfil";
        }
        String usuarioAlta;
        if (usuario == null) {
        	usuarioAlta = form.getEmail();
        }
        else {
        	usuarioAlta = usuario.getUsername();
        }
        estudianteService.grabar(form, usuarioAlta);

        return "redirect:/estudiante/editar-perfil?sucess";
    }
    
    @PostMapping("/estudiantes/miperfil")
    public String actualizarPerfil(
            @Valid @ModelAttribute("form") EstudianteDTO form,
            BindingResult result,
            Principal principal,
            Model model) {
    	if (principal.getName() != form.getEmail()){
    		result.rejectValue("errorGlobal",  "error.en properties", "SÓLO SE PUEDE MODIFICAR EL PERFIL POR EL PROPIO USUARIO");
    	}
        if (result.hasErrors()) {
        	model.addAttribute("url", "/estudiantes/miperfil");
            return "estudiante/editar-perfil";
        }

        //RegistroEstudianteMapper.updateFromPerfilDTO(form, usuario, estudiante);

        // TODO enviar el DTO alservice
        //usuarioService.guardar(usuario);
        // TODO enviar el DTO alservice
        //estudianteService.guardar(form);

        return "redirect:/estudiante/miperfil?exito";
    }
    @GetMapping("/estudiantes/lista")
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.findAll());
        return "estudiante/lista-estudiantes";
    }

}

