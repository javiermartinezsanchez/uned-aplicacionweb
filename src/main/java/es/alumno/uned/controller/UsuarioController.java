package es.alumno.uned.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.dto.UserPasswordChangeDTO;
import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.mapper.UsuarioRegistroMapper;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.util.PaginacionComun;
import es.alumno.uned.service.RolService;
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
	@Autowired
	private PasswordEncoder passwEncoder;

	private RolService rolService;
	public UsuarioController(UsuarioService userService, RolService rolService) {
		super();
		this.userService = userService;
		this.rolService = rolService;
	}
	@GetMapping("/admin/usuario")
	public String listaUsuariosPaginada(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest= PageRequest.of(page, 10);
		PaginacionComun<Usuario> paginacion = userService.listadoPaginado("/admin/usuario", pageRequest);
		model.addAttribute("titulo", "{usuario.lista}");
		model.addAttribute("urlAlta", "/admin/newUser");
		model.addAttribute("usuarios", userService.users2DTO(paginacion.getPagina().getContent()));
		model.addAttribute("paginacion", true);
		model.addAttribute("pagina", paginacion);
		return "admin/usuarios";
	}
	@GetMapping("/admin/newUser")
	public String nuevoUsuario(Model model) {
    	model.addAttribute("url", "/admin/usuario");
        model.addAttribute("form", new UsuarioRegistroDTO());
        model.addAttribute("rol", rolService.getList());
		return "admin/usuario";
	}

	@PostMapping("/admin/usuario")
	public String grabarUsuario(@AuthenticationPrincipal UserDetails usuario,
            @Valid @ModelAttribute("form") UsuarioRegistroDTO form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
        	model.addAttribute("url", "/admin/usuario");
            return "admin/usuario";
        }
        userService.grabar(form,usuario.getUsername());
		return "admin/usuarios";
	}
	@GetMapping("/admin/usuario/{id}")
    public String modUsuario(Model model, @PathVariable("id") Long id) {
		model.addAttribute("url", "/admin/usuario");
		model.addAttribute("form", userService.getUsuario(id));
		model.addAttribute("roles", rolService.getList());
		return "admin/usuario";
	}
	@GetMapping("/admin/usuarioconnected")
    public String modUsuario(Model model) {

		
		model.addAttribute("usuarios", userService.getConnectedUsers());
		return "admin/usuariosconectados";
	}
	@GetMapping("/cambiopassword")
	public String showPasswordForm(Model model) {
	    model.addAttribute("form", new UserPasswordChangeDTO());
	    return "/comun/cambio-password";
	}

	@PostMapping("/cambiopassword")
	public String changePassword(@AuthenticationPrincipal UserDetails userDetails,
	                             @Valid @ModelAttribute UserPasswordChangeDTO dto,
	                             BindingResult result,
	                             Model model) {

	    if (result.hasErrors()) {
	    	return "/comun/cambio-password";
	    }

	    Usuario usuario = userService.findByEmail(userDetails.getUsername());

	    // Validar contraseña actual
	    if (!passwEncoder.matches(dto.getOldPassword(), usuario.getPassword())) {
	        result.rejectValue("oldPassword", "error.oldPassword", "{password.current.invalid}");
	        return "/comun/cambio-password";
	    }

	    // Validar coincidencia
	    if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
	        result.rejectValue("confirmPassword", "error.confirmPassword", "{password.new.mismatch}");
	        return "/comun/cambio-password";
	    }

	    // Guardar nueva contraseña
	    usuario.setPassword(passwEncoder.encode(dto.getNewPassword()));
	    userService.grabar(UsuarioRegistroMapper.toDTO(usuario), usuario.getEmail());

	    model.addAttribute("success", "{password.change.success}");
	    return "/comun/cambio-password";
	}
	@GetMapping("/admin/usuario/{id}/password")
	public String showAdminPasswordForm(@PathVariable Long id, Model model) {
	    model.addAttribute("userId", id);
	    model.addAttribute("passwordDTO", new UserPasswordChangeDTO());
	    return "admin/password-change";
	}

	@PostMapping("/admin/usuario/{id}/password")
	public String changeUserPassword(@PathVariable Long id,
	                                 @Valid @ModelAttribute("passwordDTO") UserPasswordChangeDTO dto,
	                                 BindingResult result,
	                                 Model model) {

	    if (result.hasErrors()) {
	        return "admin/password-change";
	    }

	    if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
	        result.rejectValue("confirmPassword", "error.confirmPassword", "{password.new.mismatch}");
	        return "admin/password-change";
	    }

	    Usuario usuario = userService.findById(id);
	    usuario.setPassword(passwEncoder.encode(dto.getNewPassword()));
	    userService.grabar(UsuarioRegistroMapper.toDTO(usuario), usuario.getEmail());

	    model.addAttribute("success", "{password.change.success}");
	    return "admin/password-change";
	}

}
