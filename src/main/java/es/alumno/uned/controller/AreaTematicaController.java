package es.alumno.uned.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.dto.PerfilEstudianteDTO;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.util.PaginacionComun;
import es.alumno.uned.service.AreaTematicaService;
import jakarta.validation.Valid;

@Controller
public class AreaTematicaController {

	AreaTematicaService areaTematicaService;
	
	public AreaTematicaController(AreaTematicaService areaTematicaService) {
		this.areaTematicaService = areaTematicaService;
	}
	@GetMapping("/admin/newAreaTematica")
	public String nuevaArea(Model model) {
		model.addAttribute("url", "/admin/areaTematica");
		model.addAttribute("form", new AreaTematicaDTO());
		return "admin/areaTematica";
	}

	@GetMapping("/admin/areaTematica/{id}")
	public String consultaArea(@PathVariable("id") Long id, Model model) {
		model.addAttribute("url", "/admin/areaTematica");
		model.addAttribute("form", areaTematicaService.getAreaTematica(id));
		return "admin/areaTematica";
	}
	
	@PostMapping("/admin/areaTematica")
	public String altaArea(@Valid @ModelAttribute("form") AreaTematicaDTO form,
            BindingResult result,Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("url", "/admin/areaTematica");
            return "admin/areaTematica";
        }
        if (form.getId() == null) {
        	model.addAttribute("form",areaTematicaService.nuevaArea(form));
        }
        else {
        	model.addAttribute("form",areaTematicaService.grabar(form));
        }
		//model.addAttribute("url", "/admin/areaTematica");
		//model.addAttribute("form", areaTematicaService.getAreaTematica(id));
		return "admin/areaTematica";
	}

	@GetMapping("/admin/AreaTematica")
	public String list(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest= PageRequest.of(page, 10);
		PaginacionComun<AreaTematica> paginacion = areaTematicaService.listadoPaginado("/admin/areaTematica", pageRequest);
		model.addAttribute("pagina", paginacion);
		return "admin/AreaTematicaList";
	}

}
