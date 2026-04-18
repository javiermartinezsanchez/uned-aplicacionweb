package es.alumno.uned.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.util.PaginacionComun;
import es.alumno.uned.service.UserDetailsServiceImpl;
import es.alumno.uned.service.UsuarioService;
import jakarta.validation.Valid;

/**
 * Controlador de mantenimiento de usuarios.
 * 
 * Accedemos al listado y mantenimiento de usuarios para el Administrador.
 * Se accede al registro y la modificación en "mi Perfil" para cada usuario.
 */
@Slf4j
@Controller
public class UsuarioController {

	
	private UsuarioService userService;
	
	
	public UsuarioController(UsuarioService userService) {
		super();
		this.userService = userService;
	}
	@GetMapping("/admin/usuario")
    public String listaUsuarios(Model model) {
		model.addAttribute("usuarios", userService.listarUsuarios());
		model.addAttribute("paginacion", false);
		return "admin/usuarios";
	}
	
	@GetMapping("/admin/usuario/page")
	public String listaUsuariosPaginada(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest= PageRequest.of(page, 10);
		PaginacionComun<Usuario> paginacion = userService.listadoPaginado("/admin/usuario/page", pageRequest);
		//PaginacionComun<Usuario> paginacion = new PaginacionComun<>("/admin/usuario/page", users);
		model.addAttribute("titulo", "Usuarios Pag");
		model.addAttribute("usuarios", userService.users2DTO(paginacion.getPagina().getContent()));
		model.addAttribute("paginacion", true);
		model.addAttribute("pagina", paginacion);
		return "admin/usuarios";
	}
	@GetMapping("/admin/newUser")
	public String nuevoUsuario(Model model) {
    	model.addAttribute("url", "/admin/newUser");
        model.addAttribute("form", new UsuarioRegistroDTO());
		return "admin/usuario";
	}

	@PostMapping("/admin/newUser")
	public String grabarUsuario(@AuthenticationPrincipal UserDetails usuario,
            @Valid @ModelAttribute("form") UsuarioRegistroDTO form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
        	model.addAttribute("url", "/admin/newUser");
            return "admin/usuario";
        }
        userService.grabar(form,usuario.getUsername());
		return "admin/usuarios";
	}
	@GetMapping("/admin/usuario/{id}")
    public String modUsuario(Model model, @PathVariable("id") Long id) {
		model.addAttribute("url", "/admin/usuario");
		model.addAttribute("form", userService.getUsuario(id));
		return "admin/usuario";
	}
}
