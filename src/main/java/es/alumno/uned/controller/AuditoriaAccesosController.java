package es.alumno.uned.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.service.UserAuditService;

@Controller
public class AuditoriaAccesosController extends BaseCrudController{

	UserAuditService auditService;
	
	public AuditoriaAccesosController(UserAuditService auditService) {
		this.auditService = auditService;
	}
	@GetMapping("/admin/accesos")
    public String listaAccesos(
            @RequestParam Map<String, String> params,
    		@RequestParam(name="page", defaultValue = "0") int page, 
    		Model model) {

		var paginacion = auditService.listadoPaginado(  getParams(page), paramsToMap(params));

		model.addAttribute("titulo", "Auditoria Accesos");
		model.addAttribute("urlBack", "/home");
		model.addAttribute("paginacion", paginacion);
		return "admin/accesos";
	}

}
