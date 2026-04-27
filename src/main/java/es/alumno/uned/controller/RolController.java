package es.alumno.uned.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.alumno.uned.service.RolService;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador de mantenimiento de ROLES (Sólo a efectos de admin).
 * 
 * Accedemos al listado y mantenimiento de usuarios para el Administrador.
 * Se accede al registro y la modificación en "mi Perfil" para cada usuario.
 */
@Slf4j
@Controller
public class RolController {

	private RolService rolService;

	public RolController(RolService rolService) {
		super();
		this.rolService = rolService;
	}

	@GetMapping("/admin/rol")
	public String listaRol(Model model) {
		model.addAttribute("urlBack", "/home");
	    model.addAttribute("lista", rolService.getList());
		return "admin/roles";
	}
}
