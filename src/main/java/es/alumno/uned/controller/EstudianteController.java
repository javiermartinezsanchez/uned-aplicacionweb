package es.alumno.uned.controller;

import es.alumno.uned.dto.PerfilEstudianteDTO;
import es.alumno.uned.dto.RegistroEstudianteDTO;
import es.alumno.uned.mapper.RegistroEstudianteMapper;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.service.EstudianteService;
import es.alumno.uned.service.UsuarioService;
import jakarta.validation.Valid;

import java.security.Principal;

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
        model.addAttribute("form", new RegistroEstudianteDTO());
        return "estudiantes/editar-perfil";
    }


    @PostMapping("/registro")
    public String registrar(
            @Valid @ModelAttribute("form") RegistroEstudianteDTO form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
        	model.addAttribute("url", "/registro");
            return "/registro";
        }

        estudianteService.guardar(form);

        return "redirect:/estudiantes/editar-perfil?exito";
    }
    
    @GetMapping("/estudiantes/miperfil")
    public String editarPerfil(Model model, Principal principal) {

        Usuario usuario = usuarioService.findByEmail(principal.getName());
        Estudiante estudiante = estudianteService.findById(usuario.getId());

        PerfilEstudianteDTO dto = RegistroEstudianteMapper.toPerfilDTO(usuario, estudiante);
        
    	model.addAttribute("url", "/estudiantes/miperfil");
        model.addAttribute("form", dto);
        return "estudiantes/editar-perfil";
    }

    @PostMapping("/estudiantes/miperfil")
    public String actualizarPerfil(
            @Valid @ModelAttribute("form") PerfilEstudianteDTO form,
            BindingResult result,
            Principal principal,
            Model model) {

        if (result.hasErrors()) {
        	model.addAttribute("url", "/estudiantes/miperfil");
            return "estudiantes/editar-perfil";
        }

        Usuario usuario = usuarioService.findByEmail(principal.getName());
        Estudiante estudiante = estudianteService.findById(usuario.getId());

        RegistroEstudianteMapper.updateFromPerfilDTO(form, usuario, estudiante);

        // TODO enviar el DTO alservice
        //usuarioService.guardar(usuario);
        // TODO enviar el DTO alservice
        //estudianteService.guardar(estudiante);

        return "redirect:/estudiantes/miperfil?exito";
    }
    @GetMapping("/estudiantes/lista")
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.findAll());
        return "estudiantes/lista-estudiantes";
    }

}

