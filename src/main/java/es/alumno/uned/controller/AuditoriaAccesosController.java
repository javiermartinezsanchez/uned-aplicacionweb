package es.alumno.uned.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.dto.AccesoPorDia;
import es.alumno.uned.service.UserAuditService;
/**
 * Controlador de la Auditoría de Accesos.
 */
@Controller
@RequestMapping("/admin")
public class AuditoriaAccesosController extends BaseCrudController{

	UserAuditService auditService;
	
	public AuditoriaAccesosController(UserAuditService auditService) {
		this.auditService = auditService;
	}
	/**
	 * Consulta de la auditoría de acceso.
	 * 
	 * @param params
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/accesos")
    public String listaAccesos(
            @RequestParam Map<String, String> params,
    		@RequestParam(name="page", defaultValue = "0") int page, 
    		Model model) {

		var paginacion = auditService.listadoPaginado(  getParams(page), paramsToMap(params));

		model.addAttribute("titulo", "Auditoria Accesos");
		setModeloListado(model, "admin/accesos", 
				"/home",
				"/home", 
				"/home");
		model.addAttribute("paginacion", paginacion);
		return model.getAttribute("viewName").toString();
	}

	/**
	 * Generamos el gráfico por días de accesos.
	 * @param params Parámetros de la búsqueda de fechas.
	 * @param model Modelo contenedor de los datos.
	 * @return Vista dónde se muestra los datos.
	 */
	@GetMapping("/auditoria")
	public String mostrarAuditoria(@RequestParam Map<String, String> params,
			Model model) {
	    List<AccesoPorDia> resultados = auditService.contarAccesosPorDia(paramsToMap(params));

	    long maxAccesos = resultados.stream()
	            .mapToLong(AccesoPorDia::getCantidad)
	            .max()
	            .orElse(1); // Evitamos división por cero si está vacío
	    model.addAttribute("urlBack", "/accesos");
	    model.addAttribute("resultados", resultados);
	    model.addAttribute("maxAccesos", maxAccesos);
	    
	    return "admin/auditoria-accesos";
	}
}
