package es.alumno.uned.controller;

import java.time.LocalDate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.util.PaginacionComun;
import es.alumno.uned.service.UserAuditService;

@Controller
public class AuditoriaAccesosController {

	UserAuditService auditService;
	
	public AuditoriaAccesosController(UserAuditService auditService) {
		this.auditService = auditService;
	}
	@GetMapping("/admin/accesos")
    public String listaAccesos(
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaIni,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin,
    		@RequestParam(name="page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest= PageRequest.of(page, 10);
		PaginacionComun<UserAudit> paginacion = auditService.listadoPaginado("/admin/accesos", pageRequest, fechaIni, fechaFin);
		model.addAttribute("fechaIni", fechaIni);
	    model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("titulo", "Auditoria Accesos");
		model.addAttribute("pagina", paginacion);
		return "admin/accesos";
	}

}
