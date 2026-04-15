package es.alumno.uned.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import es.alumno.uned.service.UserDetailsServiceImpl;
import es.alumno.uned.service.UsuarioService;

/**
 * Controlador de mantenimiento de usuarios.
 * 
 * Accedemos al listado y mantenimiento de usuarios para el Administrador.
 * Se accede al registro y la modificación en "mi Perfil" para cada usuario.
 */
@Controller
public class UsuarioController {

	
	private UserDetailsServiceImpl userService;
	
	
	public UsuarioController(UserDetailsServiceImpl userService) {
		super();
		this.userService = userService;
	}
	@GetMapping("/admin/usuario")
    public String listaUsuarios(Model model) {
		model.addAttribute("usuarios", userService.listarUsuarios());
		
		return "usuarios";
	}
	@GetMapping("/admin/newUser")
	public String nuevoUsuario() {
		
		return "usuario";
	}

	@PostMapping("/admin/newUser")
	public String grabarUsuario() {
		
		return "usuarios";
	}

}
