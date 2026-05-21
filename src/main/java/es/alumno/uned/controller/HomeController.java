package es.alumno.uned.controller;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.model.util.UserUtil;
import es.alumno.uned.service.AreaTematicaService;
import es.alumno.uned.service.CursoService;
import es.alumno.uned.service.EstudianteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.groups.Default;

/**
 * Controlador inicial de la aplicación.
 * 
 * Para acceso inicial anónimo "/" y para el reencaminar al login, registro o modificación de datos.
 */
@Controller
public class HomeController extends BaseCrudController {

	private CursoService cursoService;
	private final EstudianteService estudianteService;
	private final AreaTematicaService areaService;
	public HomeController(CursoService cursoService, 
			EstudianteService estudianteService,
			AreaTematicaService areaService) {
		this.cursoService = cursoService;
		this.estudianteService= estudianteService;
		this.areaService =areaService;
	}
	
	@Autowired 
    SmartValidator validator;
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

	   		Paginacion<Curso, CursoDTO> paginacion = cursoService.listadoOrderByNumVisistas( getParams( page, 3), paramsToMap(paramsBusqueda));
	   		Paginacion<Curso, CursoDTO> paginacionMV = cursoService.listadoOrderByValoracion( getParams( page, 3), paramsToMap(paramsBusqueda));
	   		Paginacion<Curso, CursoDTO> paginacionME = cursoService.listadoOrderByInscritos( getParams( page, 3), paramsToMap(paramsBusqueda));

	   		modelo.addAttribute("paginacion", paginacion);
	   		modelo.addAttribute("paginacionMV", paginacionMV);
	   		modelo.addAttribute("paginacionME", paginacionME);
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
	@GetMapping("/miperfil")
	public String miperfil(Authentication authentication) {
		return String.format("redirect:/%s/miperfil", UserUtil.defineHome(UserUtil.getRoles(authentication)));
	}
    @GetMapping("/registro")
    public String nuevo(Model model) {
    	setModeloFormulario(model,"estudiante/editar-perfil","/registro","/home");
        model.addAttribute("form", new EstudianteDTO("ESTUD"));
        model.addAttribute("title", "Sign up");
        return model.getAttribute("viewName").toString();
    }
    @PostMapping("/registro")
    public String registrar(
    		@AuthenticationPrincipal SecurityUser userConnected,
    		@RequestParam(required = false) List<Long> areasSeleccionadas,
    		@ModelAttribute("form") EstudianteDTO form,
            BindingResult result,
            HttpServletRequest request,
            Model model) {
    	setRequestFormulario(request, "estudiante/editar-perfil","/registro", "/", "/login");
    	validator.validate(form, result, Default.class); 
        if (result.hasErrors()) {
            return request.getAttribute("viewName").toString();
        }
        String usuarioAlta = "";
        
        if (userConnected == null) {
        	usuarioAlta = form.getEmail();
        }
        else {
        	usuarioAlta = userConnected.getUsername();
        }
        if (areasSeleccionadas != null) {
	        List<AreaTematicaDTO> nuevasAreas = areaService.findAllById(areasSeleccionadas);
	        form.setAreasInteres(nuevasAreas);
        }
        estudianteService.grabar(form, usuarioAlta);
        model.addAttribute("success", "mensaje.grabacionOK");
        return "redirect:" + request.getAttribute("urlRedirect") + "?sucess";
    }

}
