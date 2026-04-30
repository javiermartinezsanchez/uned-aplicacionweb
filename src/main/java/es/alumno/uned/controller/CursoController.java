package es.alumno.uned.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.dto.ValoracionDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.util.ControllerUtil;
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
		model.addAttribute("url", "/curso/curso");
		model.addAttribute("urlCancel", "/curso");
	    model.addAttribute("curso", new CursoDTO());
	    model.addAttribute("areas", areaTematicaService.listAll());
	    model.addAttribute("usuarios", usuarioService.listarProfesores());
	    return "curso/curso";
	}
	
	@GetMapping("/curso/curso/{id}")
    public String modCurso(Model model, 
    		@PathVariable("id") Long id) {
		model.addAttribute("url", "/curso/curso");
		model.addAttribute("urlCancel", "/curso");
		model.addAttribute("form", cursoService.getCurso(id));
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
	        return "redirect:/curso";
	    }

	    cursoService.grabar(dto, imagen, userDetails.getUsername());
	    return "redirect:/curso";
	}
    
    @GetMapping("/curso")
    public String ListadoGeneral(
    		@RequestParam Map<String, String> params,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer nivel,
            @RequestParam(required = false) Long areaId,
            @RequestParam(required = false) Long responsableId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fIni,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fFin,
            @RequestParam(name="page", defaultValue = "0") int page,
            Model model) {
    	Pageable pageRequest= PageRequest.of(page, 10);
        Paginacion<Curso, CursoDTO> paginacion;
        var filtros = ControllerUtil.paramsToMap(params);
        // ============================
        //   LÓGICA DE BÚSQUEDA
        // ============================

        if (titulo != null && !titulo.isBlank()) {
            paginacion = cursoService.listadoPaginadoPorTitulo("/curso", pageRequest, titulo);
        }
        else if (nivel != null) {
            paginacion = cursoService.listadoPaginadoPorNivel("/curso", pageRequest, nivel);
        }
        else if (areaId != null) {
            paginacion = cursoService.listadoPaginadoPorArea("/curso", pageRequest, areaId);
        }
        else if (responsableId != null) {
            paginacion = cursoService.listadoPaginadoPorResponsable("/curso", pageRequest, responsableId);
        }
        else if (fIni != null && fFin != null) {
            paginacion = cursoService.listadoPaginadoPorFechaInicio(
                    "/curso", pageRequest,
                    fIni.atStartOfDay(),
                    fFin.atTime(23, 59)
            );
        }
        else {
            paginacion = cursoService.listadoPaginado("/curso", pageRequest);
        }

        // ============================
        //   DATOS PARA EL FORMULARIO
        // ============================
        model.addAttribute("urlAlta", "/curso/nuevo");
        model.addAttribute("paginacion", paginacion);
        model.addAttribute("areas", areaTematicaService.listAll());
        model.addAttribute("responsables", usuarioService.listarProfesores());
        model.addAttribute("query", ControllerUtil.mapToQuery(filtros));

        return "curso/cursos"; 
    	
    }
	@PostMapping("/valoracionCurso")
    @ResponseBody // CRUCIAL: Indica que el retorno es JSON, no una vista HTML
    public ResponseEntity<Map<String, Object>> procesarValoracion(@RequestBody ValoracionDTO datos) {
        
        // 1. Validaciones
        if (datos.getValoracion() == null || datos.getValoracion() < 1 || datos.getValoracion() > 5) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("mensaje", "La valoración debe ser un número entre 1 y 5.");
            return ResponseEntity.badRequest().body(error);
        }

        // 2. Lógica de Negocio (Simulada)
        // boolean guardado = valoracionService.guardarValoracion(datos.getIdElemento(), datos.getValoracion());
        boolean guardado = true; // Simulación de éxito

        // 3. Construcción de la respuesta JSON
        Map<String, Object> respuesta = new HashMap<>();
        System.out.println("datos.getIdElemento(): " + datos.getIdElemento());
        System.out.println("datos.getValoracion(): " + datos.getValoracion());
        if (guardado) {
            respuesta.put("success", true);
            respuesta.put("mensaje", "¡Gracias por tu valoración!");
            // Opcional: devolver la nueva valoración promedio si quieres actualizarla en el frontend
            // respuesta.put("nuevaPromedio", 4.5); 
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.put("success", false);
            respuesta.put("mensaje", "Hubo un error al guardar la valoración.");
            return ResponseEntity.internalServerError().body(respuesta);
        }
    }
}
