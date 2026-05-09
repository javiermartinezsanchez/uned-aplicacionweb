package es.alumno.uned.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.model.util.UserUtil;
import es.alumno.uned.service.CursoService;

/**
 * Controlador inicial de la aplicación.
 * 
 * Para acceso inicial anónimo "/" y para el reencaminar al login, registro o modificación de datos.
 */
@Controller
public class HomeController {

	private CursoService cursoService;
	
	public HomeController(CursoService cursoService) {
		this.cursoService = cursoService;
	}
	/**
	 * Acceso a la página principal.
	 * @param modelo Modelo de la vista
	 * @param authentication {@code Authentication} Injectada por Spring
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
		else {
			modelo.addAttribute("cursos", cursoService.listadoHome());
		}
		return urlUser.concat("/home");
	}
	@GetMapping("/home")
	public String accesoStandard(Model modelo, Authentication authentication) {
		return handleAnonimousHome(modelo, authentication);
	}
	@GetMapping("/login")
	public String handleLogin(@RequestParam(required = false) String success,
			Model model) {
		if (success != null) {
			model.addAttribute("success", "mensaje.grabacionOK");
			}
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
	@GetMapping("/miperfil")
	public String miperfil(Authentication authentication) {
		return String.format("redirect:/%s/miperfil", UserUtil.defineHome(UserUtil.getRol(authentication)));
	}

}
