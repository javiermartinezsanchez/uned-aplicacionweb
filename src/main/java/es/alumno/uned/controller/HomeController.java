package es.alumno.uned.controller;



import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.util.ControllerUtil;
import es.alumno.uned.model.util.Paginacion;
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
	public String handleAnonimousHome(Authentication authentication,
			@RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(name="page", defaultValue = "0") int page,
			Model modelo) {
		modelo.addAttribute("title", "Home");
		String urlUser= "";
		if (authentication !=null) {
		 urlUser =  UserUtil.defineHome(UserUtil.getRoles(authentication));
		 modelo.addAttribute("urlUser", "admin");
		}
		else {
	   		Pageable pageRequest= PageRequest.of(page, 3, Sort.by("numVistas").descending());
	   		var filtros = ControllerUtil.paramsToMap(paramsBusqueda);
	   		Paginacion<Curso, CursoDTO> paginacion = cursoService.listadoPaginado("/", pageRequest, filtros);
	   		modelo.addAttribute("paginacion", paginacion);
	        modelo.addAttribute("query", ControllerUtil.mapToQuery(filtros));
		}
		return urlUser.concat("/home");
	}
	/**
	 * Método que redirige al /home de cada rol.
	 * @param modelo Modelo stándar de Spring.
	 * @param authentication {@link Authentication} del usuario actual.
	 * @return Localización del la página "home" de acuerdo al rol de cada usuario.
	 */
	@GetMapping("/home")
	public String accesoStandard(Authentication authentication, @RequestParam Map<String, String> paramsBusqueda,
			@RequestParam(name="page", defaultValue = "0") int page,Model modelo ) {
		return handleAnonimousHome( authentication, null, 0, modelo);
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
		return "admin/home";
	}

	@GetMapping("/profesor/home")
	public String profeHome(Model modelo) {
		return "profesor/home";
	}
	@GetMapping("/estudiante/home")
	public String estudianteHome(Model modelo) {
		return "estudiante/home";
	}
	@GetMapping("/miperfil")
	public String miperfil(Authentication authentication) {
		return String.format("redirect:/%s/miperfil", UserUtil.defineHome(UserUtil.getRoles(authentication)));
	}

}
