package es.alumno.uned.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
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
/**
 * Controlador de Areas Temáticas.
 * 
 * <p>Se definen la operaciones básicas sobre  AreaTematica
 * 
 */
@Controller
public class AreaTematicaController extends BaseCrudController{

	AreaTematicaService areaTematicaService;
	
	public AreaTematicaController(AreaTematicaService areaTematicaService) {
		this.areaTematicaService = areaTematicaService;
	}
	/**
	 * Inicializa un Area Temática para darla de alta.
	 * <p>Se realiza un {@code @Preautorize} para blindar el alta.
	 * <p>Se genera un nuevo {@link AreaTematicaDTO}
	 * 
	 * @param urlBase Identificador del rol que 
	 * @param model {@link Model} Modelo de la vista.
	 * @return Vista que vamos a utilizar.
	 */
	@GetMapping("/{urlBase}/areaTematica/nueva")
	@PreAuthorize(
		    "(#urlBase == 'admin' and hasRole('ADMIN')) or " +
		    "(#urlBase == 'profesor' and hasRole('PROFE'))"
		)
	public String nueva(@PathVariable("urlBase") String urlBase,
			Model model) {
		preparaArea(model, urlBase);
		model.addAttribute("form", new AreaTematicaDTO());
		return model.getAttribute("viewName").toString();
	}
	/**
	 * Consulta de una Area Temática por id.
	 * <p>Se realiza un {@code @Preautorize} para blindar el acceso a la alta.
	 * @param urlBase Parte inicial de la url que define el rol que accede.
	 * @param id Identificador del Area a consultar/modificar.
	 * @param successStr mensaje de éxito de la operación para indicarlo al usuario.
	 * @param model Modelo que viaja a la vista con los datos necesarios.
	 * @return Vista que utilizaremos para la edición.
	 */
	@GetMapping("/{urlBase}/areaTematica/{id}")
	@PreAuthorize(
		    "(#urlBase == 'admin' and hasRole('ADMIN')) or " +
		    "(#urlBase == 'profesor' and hasRole('PROFE'))"
		)
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
	/**
	 * Grabación de los datos del Area Temática. 
	 * <p> Si es un alta, el id no vendrá informado, en los demás casos se realizará la modificación de los datos introducidos.
	 * @param urlBase Parte inicial de la url que define el rol que accede.
	 * @param form Datos del formulario que se ha modificado.
	 * @param result Se genera el {@link BindingResult} para validación de los campos.
	 * @param redirectAttributes Añadimos atributos que no existen en el formulario de datos.
	 * @param model Model a tratar.
	 * @return Redirección a la vista de consulta con el mensaje "sucess".
	 */
	@PostMapping("/{urlBase}/areaTematica")
	@PreAuthorize(
		    "(#urlBase == 'admin' and hasRole('ADMIN')) or " +
		    "(#urlBase == 'profesor' and hasRole('PROFE'))"
		)	
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
	@PreAuthorize(
		    "(#urlBase == 'admin' and hasRole('ADMIN')) or " +
		    "(#urlBase == 'profesor' and hasRole('PROFE'))"
		)
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
	/**
	 * Método privado para definir los valores utilizados en el modelo.
	 * @param model Modelo a definir.
	 * @param urlBase Determina el rol que está realizado la operación.
	 */
	private void preparaArea(Model model, String urlBase) {
		setModeloFormulario(model, "admin/areaTematica", "/"+urlBase+"/areaTematica","/"+urlBase+"/areaTematica");
	}
}
