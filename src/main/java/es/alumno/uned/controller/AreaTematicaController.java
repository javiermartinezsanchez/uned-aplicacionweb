package es.alumno.uned.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.util.PaginacionComun;
import es.alumno.uned.service.AreaTematicaService;

@Controller
public class AreaTematicaController {

	AreaTematicaService areaTematicaService;
	
	@GetMapping("/admin/newAreaTematica")
	public String nuevaArea(Model model) {
		model.addAttribute("url", "/admin/AreaTematica");
		model.addAttribute("form", new AreaTematicaDTO());
		return "admin/AreaTematica";
	}

	@GetMapping("/admin/AreaTematica/{id}")
	public String consultaArea(@PathVariable("id") Long id, Model model) {
		model.addAttribute("url", "/admin/AreaTematica");
		model.addAttribute("form", areaTematicaService.getAreaTematica(id));
		return "admin/AreaTematica";
	}
	
	@GetMapping("/admin/AreaTematica")
	public String list(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest= PageRequest.of(page, 10);
		PaginacionComun<AreaTematica> paginacion = areaTematicaService.listadoPaginado("/admin/AreaTematica", pageRequest);
		model.addAttribute("pagina", paginacion);
		return "admin/AreaTematicaList";
	}

}
