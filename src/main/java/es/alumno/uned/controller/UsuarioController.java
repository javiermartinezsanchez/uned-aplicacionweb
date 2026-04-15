package es.alumno.uned.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.util.PaginacionComun;
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
		model.addAttribute("paginacion", false);
		return "usuarios";
	}
	
	@GetMapping("/admin/usuario/page")
	public String listaUsuariosPaginada(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest= PageRequest.of(page, 10);
		Page<Usuario> users = userService.listadoPaginado(pageRequest);
		
		PaginacionComun<Usuario> paginacion = new PaginacionComun<>("/admin/usuario/page", users);
		model.addAttribute("titulo", "Usuarios Pag");
		model.addAttribute("usuarios", users);
		model.addAttribute("paginacion", true);
		model.addAttribute("pagina", paginacion);
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
