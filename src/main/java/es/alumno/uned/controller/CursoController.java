package es.alumno.uned.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
    @GetMapping("/curso")
    public String ListadoGeneral(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer nivel,
            @RequestParam(required = false) Long areaId,
            @RequestParam(required = false) Long responsableId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fIni,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fFin,
            Pageable pageable,
            Model model) {

        Paginacion<Curso, CursoDTO> paginacion;

        // ============================
        //   LÓGICA DE BÚSQUEDA
        // ============================

        if (titulo != null && !titulo.isBlank()) {
            paginacion = cursoService.listadoPaginadoPorTitulo("/curso", pageable, titulo);
        }
        else if (nivel != null) {
            paginacion = cursoService.listadoPaginadoPorNivel("/curso", pageable, nivel);
        }
        else if (areaId != null) {
            paginacion = cursoService.listadoPaginadoPorArea("/curso", pageable, areaId);
        }
        else if (responsableId != null) {
            paginacion = cursoService.listadoPaginadoPorResponsable("/curso", pageable, responsableId);
        }
        else if (fIni != null && fFin != null) {
            paginacion = cursoService.listadoPaginadoPorFechaInicio(
                    "/curso", pageable,
                    fIni.atStartOfDay(),
                    fFin.atTime(23, 59)
            );
        }
        else {
            paginacion = cursoService.listadoPaginado("/curso", pageable);
        }

        // ============================
        //   DATOS PARA EL FORMULARIO
        // ============================
        model.addAttribute("urlAlta", "/curso/nuevo");
        model.addAttribute("paginacion", paginacion);
        model.addAttribute("areas", areaTematicaService.listAll());
        model.addAttribute("responsables", usuarioService.listarProfesores());

        return "curso/cursos"; 
    	
    }
	
}
