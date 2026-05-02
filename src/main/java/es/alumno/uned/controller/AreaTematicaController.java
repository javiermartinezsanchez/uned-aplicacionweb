package es.alumno.uned.controller;

import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.model.util.ControllerUtil;
import es.alumno.uned.service.AreaTematicaService;
import jakarta.validation.Valid;

@Controller
public class AreaTematicaController extends BaseCrudController{

	AreaTematicaService areaTematicaService;
	
	public AreaTematicaController(AreaTematicaService areaTematicaService) {
		this.areaTematicaService = areaTematicaService;
	}
	@GetMapping("/admin/areaTematica/nueva")
	public String nueva(Model model) {
		preparaArea(model);
		model.addAttribute("form", new AreaTematicaDTO());
		return model.getAttribute("viewName").toString();
	}

	@GetMapping("/admin/areaTematica/{id}")
	public String modifica(@PathVariable("id") Long id, 
			@ModelAttribute("successStr") String successStr,
			Model model) {
		if (successStr != null && "true".equalsIgnoreCase(successStr)) {
			model.addAttribute("success", "mensaje.grabacionOK");
		}
		preparaArea(model);
		model.addAttribute("form", areaTematicaService.getAreaTematica(id));
		return model.getAttribute("viewName").toString();
	}
	
	@PostMapping("/admin/areaTematica")
	public String graba(@Valid @ModelAttribute("form") AreaTematicaDTO form,
            BindingResult result, 
            RedirectAttributes redirectAttributes, 
            Model model) {
		preparaArea(model);
        if (result.hasErrors()) {
            return model.getAttribute("viewName").toString();
        }
        var areaGrabada = areaTematicaService.grabar(form);
        model.addAttribute("form", areaGrabada);
        redirectAttributes.addFlashAttribute("successStr", "true");
		return String.format("redirect:/admin/areaTematica/%d", areaGrabada.getId());
	}

	@GetMapping("/admin/areaTematica")
	public String lista(
			@RequestParam Map<String, String> params,
			@RequestParam(defaultValue = "0") int page, 
			Model model) {
		Pageable pageRequest= PageRequest.of(page, 10);
		
		var filtros = ControllerUtil.paramsToMap(params);
		
		String titulo = filtros.containsKey("titulo") ? filtros.get("titulo") : null;
		String descripcion = filtros.containsKey("descripcion") ? filtros.get("descripcion") : null;
		var paginacion = areaTematicaService.listadoPaginado("/admin/areaTematica", titulo, descripcion, pageRequest);
		model.addAttribute("urlAlta", "/admin/areaTematica/nueva");
		model.addAttribute("urlBack", "/home");
	    model.addAttribute("paginacion", paginacion);
		model.addAttribute("titulo", titulo);
		model.addAttribute("descripcion", descripcion);

	    model.addAttribute("query", ControllerUtil.mapToQuery(filtros));
		return "admin/AreaTematicaList";
	}
	private void preparaArea(Model model) {
		prepararModeloFormulario(model, "admin/areaTematica", "/admin/areaTematica","/admin/areaTematica");
	}
}
