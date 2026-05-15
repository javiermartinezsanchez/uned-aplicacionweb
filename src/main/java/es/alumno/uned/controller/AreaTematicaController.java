package es.alumno.uned.controller;

import java.util.Map;

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
import es.alumno.uned.service.AreaTematicaService;
import jakarta.validation.Valid;

@Controller
public class AreaTematicaController extends BaseCrudController{

	AreaTematicaService areaTematicaService;
	
	public AreaTematicaController(AreaTematicaService areaTematicaService) {
		this.areaTematicaService = areaTematicaService;
	}
	@GetMapping("/{urlBase}/areaTematica/nueva")
	public String nueva(@PathVariable("urlBase") String urlBase,
			Model model) {
		preparaArea(model, urlBase);
		model.addAttribute("form", new AreaTematicaDTO());
		return model.getAttribute("viewName").toString();
	}

	@GetMapping("/{urlBase}/areaTematica/{id}")
	public String modifica(
			@PathVariable("urlBase") String urlBase,
			@PathVariable("id") Long id, 
			@ModelAttribute("successStr") String successStr,
			Model model) {
		if (successStr != null && "true".equalsIgnoreCase(successStr)) {
			model.addAttribute("success", "mensaje.grabacionOK");
		}
		preparaArea(model, urlBase);
		model.addAttribute("form", areaTematicaService.getAreaTematica(id));
		return model.getAttribute("viewName").toString();
	}
	
	@PostMapping("/{urlBase}/areaTematica")
	public String graba(@PathVariable("urlBase") String urlBase,
			@Valid @ModelAttribute("form") AreaTematicaDTO form,
            BindingResult result, 
            RedirectAttributes redirectAttributes, 
            Model model) {
		preparaArea(model, urlBase);
        if (result.hasErrors()) {
            return model.getAttribute("viewName").toString();
        }
        var areaGrabada = areaTematicaService.grabar(form);
        model.addAttribute("form", areaGrabada);
        redirectAttributes.addFlashAttribute("successStr", "true");
		return String.format("redirect:/" + urlBase + "/areaTematica/%d", areaGrabada.getId());
	}

	@GetMapping("/{urlBase}/areaTematica")
	public String lista(
			@PathVariable("urlBase") String urlBase,
			@RequestParam Map<String, String> params,
			@RequestParam(defaultValue = "0") int page, 
			Model model) {
		var paginacion = areaTematicaService.listadoPaginado(paramsToMap(params), getParams(page));

		setModeloListado(model, "admin/areasTematicas", 
				"/"+ urlBase+"/areaTematica/nueva",
	    		"/"+ urlBase + "/areaTematica", 
	    		"/home");
	    model.addAttribute("paginacion", paginacion);
	    model.addAttribute("title", "Listado de Áreas Temáticas");
		return model.getAttribute("viewName").toString();
	}
	private void preparaArea(Model model, String urlBase) {
		setModeloFormulario(model, "admin/areaTematica", "/"+urlBase+"/areaTematica","/"+urlBase+"/areaTematica");
	}
}
