package es.alumno.uned.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.model.util.ControllerUtil;
import es.alumno.uned.service.UserAuditService;

@Controller
public class AuditoriaAccesosController {

	UserAuditService auditService;
	
	public AuditoriaAccesosController(UserAuditService auditService) {
		this.auditService = auditService;
	}
	@GetMapping("/admin/accesos")
    public String listaAccesos(
            @RequestParam Map<String, String> params,
    		@RequestParam(name="page", defaultValue = "0") int page, 
    		Model model) {
		Pageable pageRequest= PageRequest.of(page, 10);
		
		var filtros = ControllerUtil.paramsToMap(params);

		LocalDate fechaIni = filtros.containsKey("fechaIni")
            ? LocalDate.parse(filtros.get("fechaIni"))
            : null;

		LocalDate fechaFin = filtros.containsKey("fechaFin")
            ? LocalDate.parse(filtros.get("fechaFin"))
            : null;

		var paginacion = auditService.listadoPaginado("/admin/accesos", pageRequest, fechaIni, fechaFin);

		model.addAttribute("titulo", "Auditoria Accesos");
		model.addAttribute("urlBack", "/home");
		model.addAttribute("paginacion", paginacion);
		model.addAttribute("fechaIni", fechaIni);
		model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("query", ControllerUtil.mapToQuery(filtros));
		return "admin/accesos";
	}

}
