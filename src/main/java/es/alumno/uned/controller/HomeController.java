package es.alumno.uned.controller;

import java.security.Principal;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.alumno.uned.model.util.UserUtil;

/**
 * Controlador inicial de la aplicación.
 * 
 * Para acceso inicial anónimo "/" y para el reencaminar al login, registro o modificación de datos.
 */
@Controller
public class HomeController {

	
	/**
	 * Acceso a la página principal.
	 * @param modelo Modelo de la vista
	 * @param Authentication Injectada por Spring
	 * @return Invocamos a la página "home" dependiendo de quién esté accediendo.
	 */
	@GetMapping("/")
	public String handleAnonimousHome(Model modelo,Authentication authentication) {
		modelo.addAttribute("title", "Home");
		String urlUser= "";
		if (authentication !=null) {
		//System.out.println("getCredentials: " + authentication.getCredentials().toString());
		 urlUser =  UserUtil.defineHome(UserUtil.getRol(authentication));
		 modelo.addAttribute("urlUser", "admin");
		}
		return urlUser.concat("/home");
	}
	@GetMapping("/home")
	public String accesoStandard(Model modelo, Authentication authentication) {
		return handleAnonimousHome(modelo, authentication);
	}
	@GetMapping("/login")
	public String handleLogin() {
		return "login";
	}
	
	@GetMapping("/admin/home")
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminHome(Model modelo) {
		modelo.addAttribute("urlUser", "admin");
		return "admin/home";
	}

	@GetMapping("/profesor/home")
	public String profeHome(Model modelo) {
		modelo.addAttribute("urlUser", "profesor");
		return "profesor/home";
	}
	@GetMapping("/estudiante/home")
	public String estudianteHome(Model modelo) {
		modelo.addAttribute("urlUser", "estudiante");
		return "estudiante/home";
	}

}
