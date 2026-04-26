package es.alumno.uned.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.dto.UserPasswordAdminChangeDTO;
import es.alumno.uned.dto.UserPasswordChangeDTO;
import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.exception.UserPasswordNotMatchException;
import es.alumno.uned.mapper.UsuarioRegistroMapper;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.model.util.PaginacionComun;
import es.alumno.uned.service.RolService;
import es.alumno.uned.service.UserDetailsServiceImpl;
import es.alumno.uned.service.UsuarioService;
import es.alumno.uned.validation.OnCreate;
import es.alumno.uned.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

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
	UsuarioRegistroMapper usuarioMapper;
	@Autowired
	private PasswordEncoder passwEncoder;
	@Autowired
	private Validator validator;
	
	private RolService rolService;
	public UsuarioController(UsuarioService userService, RolService rolService) {
		super();
		this.userService = userService;
		this.rolService = rolService;
	}
	@GetMapping("/admin/usuario")
	public String listaUsuariosPaginada(
			@RequestParam(name="page", defaultValue = "0") int page, 
			Model model) {
		Pageable pageRequest= PageRequest.of(page, 10);
		Paginacion<Usuario, UsuarioRegistroDTO> paginacion = userService.listadoPaginado("/admin/usuario", pageRequest);
		model.addAttribute("titulo", "{usuario.lista}");
		model.addAttribute("urlAlta", "/admin/newUser");
		model.addAttribute("paginacion", paginacion);
		model.addAttribute("query", "");
		return "admin/usuarios";
	}
	@GetMapping("/admin/newUser")
	public String nuevoUsuario(Model model) {
        model.addAttribute("form", new UsuarioRegistroDTO());
		return getUsuario(model);
	}
	@GetMapping("/admin/usuario/{id}")
    public String modUsuario(@RequestParam(required = false) String success, 
    		@PathVariable("id") Long id, 
    		Model model) {
		if (success != null) {
			model.addAttribute("success", "{}");
			}
		model.addAttribute("form", userService.getUsuario(id));
		return getUsuario(model);
	}

	private String getUsuario(Model model) {
    	model.addAttribute("url", "/admin/usuario");
    	model.addAttribute("urlCancel", "/admin/usuario");
        model.addAttribute("roles", rolService.getList());
    	
    	return "admin/usuario";
    }
	@PostMapping("/admin/usuario")
	public String grabarUsuario(@AuthenticationPrincipal UserDetails usuario,
            @ModelAttribute("form") UsuarioRegistroDTO form,
            BindingResult result,
            Model model) {

		  // 1) Para utilizar el mismo DTO sea 
		  //     Creación (con password)
		  //     Modificación (sin password) y que  no salte el @NotBlank del valitor
	    Class<?> grupo = (form.getId() == null) ? OnCreate.class : OnUpdate.class;

	    // 2) Validar dinámicamente según el grupo
	    new SpringValidatorAdapter(validator)
	            .validate(form, result, grupo);
	    
        if (result.hasErrors()) {
        	model.addAttribute("roles", rolService.getList());
        	model.addAttribute("url", "/admin/usuario");
            return "admin/usuario";
        }
        Long id = userService.grabar(form,usuario.getUsername()).getId();
        model.addAttribute("success", "mensaje.grabacionOK");
	

		return "redirect:/admin/usuario/".concat(id.toString());
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

		try {
			userService.cambioPassword(dto, userDetails.getUsername());
		}
		catch (UsernameNotFoundException ex) {
			result.rejectValue("errorGlobal", "error.oldPassword", ex.getMessage());
		}
		catch (UserPasswordNotMatchException ex) {
			result.rejectValue("oldPassword", "error.oldPassword", ex.getMessage());
		}
	    if (result.hasErrors()) {
	    	return "/comun/cambio-password";
	    }
	    model.addAttribute("success", "{password.change.success}");
	    return "/comun/cambio-password";
	}
	@GetMapping("/admin/usuario/{id}/password")
	public String showAdminPasswordForm(@PathVariable Long id, 
			@RequestParam(required = false) String success,
			Model model) {
	    model.addAttribute("userId", id);
	    model.addAttribute("passwordDTO", new UserPasswordAdminChangeDTO());
	    if (success != null) {
	        model.addAttribute("success", "password.change.success");
	    }
	    return "admin/password-change";
	}

	@PostMapping("/admin/usuario/{id}/password")
	public String changeUserPassword(@PathVariable Long id,
	                                 @Valid @ModelAttribute("passwordDTO") UserPasswordAdminChangeDTO dto,
	                                 BindingResult result,
	                                 Model model) {

		try {
			userService.cambioPasswordUserAdmin(dto, id);
		}
		catch (UsernameNotFoundException ex) {
			result.rejectValue("errorGlobal", "error.oldPassword", ex.getMessage());
		}
		catch (UserPasswordNotMatchException ex) {
			result.rejectValue("oldPassword", "error.oldPassword", ex.getMessage());
		}
	    if (!result.hasErrors()) {
	    	model.addAttribute("success", "{password.change.success}");
	    }
	    
	    return "admin/password-change";
	}

}
