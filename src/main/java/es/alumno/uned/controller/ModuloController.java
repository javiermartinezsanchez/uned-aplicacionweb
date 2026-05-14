package es.alumno.uned.controller;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.dto.ModuloDTO;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.entities.TipoModulo;
import es.alumno.uned.model.util.ControllerUtil;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.service.ModuloService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/modulo")
public class ModuloController extends BaseCrudController {

	ModuloService moduloService;
	
	public ModuloController(ModuloService moduloService) {
		this.moduloService = moduloService;
	}
	/**
	 * Método de listado genérico de Módulos.
	 * <p>Se realiza la consulta con los parámetros (opcionales) que se 
	 * @param paramsBusqueda Mapa con los parámetros de búsqueda.
	 * @param page Número de página del listado (por defecto 0)
	 * @param model {@link Model} Modelo de la vista.
	 * @return Vista que vamos a utilizar
	 */
	@GetMapping({"", "/"})
	public String ListadoGeneral(
	    		@RequestParam Map<String, String> paramsBusqueda,
	            @RequestParam(defaultValue = "0") int page,
	            Model model) {
	    	Pageable pageRequest= PageRequest.of(page, 10);
	        Paginacion<Modulo, ModuloDTO> paginacion;
	        var filtros = ControllerUtil.paramsToMap(paramsBusqueda);

	        paginacion = moduloService.listadoPaginado( pageRequest, filtros);

	        setModeloListado(model,"modulo/modulos", "/modulo/nuevo","/modulo/modulo/",  "/home" );
	        model.addAttribute("tipos", TipoModulo.values());
	        model.addAttribute("paginacion", paginacion);
	        model.addAttribute("paramsBusqueda",paramsBusqueda );

	        return model.getAttribute("viewName").toString(); 
	}
	/**
	 * Método para añadir un nuevo Módulo.
	 * <p>Se invoca mediate GET y con el mapping "/modulo/nuevo"
	 * <p>Se genera un nuevo {@link ModuloDTO} que se envia a la vista.
	 * @param model {@link Model} Modelo de la vista.
	 * @return Vista que vamos a utilizar.
	 */
	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		setModeloFormulario(model,"modulo/modulo", "/modulo/guardar", "/modulo");
		model.addAttribute("tipos", TipoModulo.values());
	    model.addAttribute("form", new ModuloDTO());
		return model.getAttribute("viewName").toString();
	}
	/**
	 * Método para guardar la información del Módulo.
	 * <p>Se invoca mediate POST y con el mapping "/modulo/guardar"
	 * @param userDetails Información del usuario "logado"
	 * @param form Datos introducidos en el formulario de la vista.
	 * @param result {@link BindingResult} de las validaciones de los campos.
	 * @param redirectAttributes Atributos del modelo no relacionados con el modelo.
	 * @param model {@link Model} Modelo completo enviado
	 * @return Redirigimos a la vista del Módulo correspondiente. 
	 */
	@PostMapping("/guardar")
	public String guarda(@AuthenticationPrincipal UserDetails userDetails,
	        @Valid @ModelAttribute("form") ModuloDTO form,
	        BindingResult result,
	        RedirectAttributes redirectAttributes, 
	        Model model) {
		
	    if (result.hasErrors()) {
	        return "modulo/modulo";
	    }

	    var moduloGrabado = moduloService.grabar(form, userDetails.getUsername());
	    model.addAttribute("form", moduloGrabado);
	    redirectAttributes.addFlashAttribute("success", "mensaje.grabacionOK");
		return String.format("redirect:/modulo/modulo/%d", moduloGrabado.getId());
	}
	/**
	 * Método de edición de un Modulo existente.
	 * <p>Se invoca mediate POST y con el mapping "/modulo/modulo/{id}", en que el "id" es el módulo a consultar.
	 * <p>Se genera el {@link ModuloDTO} correspondiente a la entidad encontrada.
	 * @param id Id del módulo a consultar/modificar, viene como parte de Path de consulta.
	 * @param model {@link Model} Modelo de la vista.
	 * @return Vista que vamos a utilizar.
	 */
	@GetMapping("/modulo/{id}")
    public String modifica(Model model, @PathVariable Long id) {
		setModeloFormulario(model,"modulo/modulo", "/modulo/guardar", "/modulo");
		model.addAttribute("tipos", TipoModulo.values());
		model.addAttribute("form", moduloService.get(id));
		return model.getAttribute("viewName").toString();
	}
}
