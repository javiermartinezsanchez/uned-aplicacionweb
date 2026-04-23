package es.alumno.uned.controller;

import java.io.IOException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.service.AreaTematicaService;
import es.alumno.uned.service.CursoService;
import es.alumno.uned.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
public class CursoController {

	CursoService cursoService;
	AreaTematicaService areaTematicaService;
	UsuarioService usuarioService;
	
	public CursoController(CursoService cursoService, AreaTematicaService areaTematicaService,
			UsuarioService usuarioService) {
		this.cursoService = cursoService;
		this.areaTematicaService = areaTematicaService;
		this.usuarioService = usuarioService;
	}
	
	@GetMapping("/curso/nuevo")
	public String nuevoCurso(Model model) {
	    model.addAttribute("curso", new CursoDTO());
	    model.addAttribute("areas", areaTematicaService.listAll());
	    model.addAttribute("usuarios", usuarioService.listarProfesores());
	    
	    return "curso/curso";
	}
	
	@PostMapping("/curso/guardar")
	public String guardarCurso(@AuthenticationPrincipal UserDetails userDetails,
	        @ModelAttribute("curso") @Valid CursoDTO dto,
	        BindingResult result,
	        @RequestParam("imagen") MultipartFile imagen,
	        Model model) throws IOException {

	    if (result.hasErrors()) {
	        model.addAttribute("areas", areaTematicaService.listAll());
	        model.addAttribute("usuarios", usuarioService.listarProfesores());
	        return "cursoform";
	    }

	    cursoService.nuevoCurso(dto, imagen, userDetails.getUsername());
	    return "redirect:/curso/lista";
	}
    @GetMapping("/curso")
    public String listadoCurso(@AuthenticationPrincipal UserDetails userDetails,
    		@RequestParam(name="page", defaultValue = "0") int page,
    		Model model) {
    	Pageable pageRequest= PageRequest.of(page, 10);
    	Paginacion<Curso, CursoDTO> paginacion =  cursoService.listadoPaginado("/curso", pageRequest);
    	model.addAttribute("urlAlta", "/curso/nuevo");
    	model.addAttribute("titulo", "{curso.lista}");
    	model.addAttribute("pagina", paginacion);
    	return "curso/cursos";
    }
	
}
