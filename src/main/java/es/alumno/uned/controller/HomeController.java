package es.alumno.uned.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador inicial de la aplicación.
 * 
 * Para acceso inicial anónimo "/" y para el reencaminar al login, registro o modificación de datos.
 */
@Controller
public class HomeController {

	
	/**
	 * Acceso a la página principal de usuarios anónimos y de estudiantes.
	 * @param modelo
	 * @return Invocamos a la página "home" principal.
	 */
	@GetMapping("/")
	public String handleAnonimousHome(Model modelo) {
		modelo.addAttribute("title", "Home");
		//modelo.addAttribute("cursos", servicio.listarCursos());
		System.out.println("Locale actual: " + LocaleContextHolder.getLocale());
		return "home";
	}
	@GetMapping("/home")
	public String accesoStandard(Model modelo) {
		return handleAnonimousHome(modelo);
	}
	@GetMapping("/login")
	public String handleLogin() {
		return "login";
	}
	
	@GetMapping("/admin/home")
	public String adminHome(Model modelo) {
		modelo.addAttribute("urlUser", "admin");
		return "admin/home";
	}

	@GetMapping("/profesor/home")
	public String profeHome(Model modelo) {
		modelo.addAttribute("urlUser", "profesor");
		return "profesor/home";
	}

}
