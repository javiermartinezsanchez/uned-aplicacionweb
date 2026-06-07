package es.alumno.uned.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import es.alumno.uned.mapper.UsuarioRegistroMapper;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.service.RolService;
import es.alumno.uned.service.UsuarioService;
import es.alumno.uned.validation.OnCreate;
import es.alumno.uned.validation.OnUpdate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

/**
 * Controlador de mantenimiento de usuarios.
 * 
 * Accedemos al listado y mantenimiento de usuarios para el Administrador.
 * Se accede al registro y la modificación en "mi Perfil" para cada usuario.
 */

@Controller
public class UsuarioController extends BaseCrudController {

	private UsuarioService userService;

	@Autowired
	UsuarioRegistroMapper usuarioMapper;

	@Autowired
	private Validator validator;
	
	private RolService rolService;
	public UsuarioController(UsuarioService userService, RolService rolService) {
		super();
		this.userService = userService;
		this.rolService = rolService;
	}
	/**
	 * Listado de usuarios para el administrador.
	 * @param params Parámetros de búsqueda.
	 * @param page Número de página.
	 * @param model Modelo para la vista.
	 * @return Vista de los datos.
	 */
	@GetMapping("/admin/usuario")
	public String listar(
			@RequestParam Map<String, String> params,
			@RequestParam(defaultValue = "0") int page, 
			Model model) {
		Paginacion<Usuario, UsuarioRegistroDTO> paginacion = userService.listadoPaginado(paramsToMap(params), getParams(page));
		model.addAttribute("titulo", "usuario.lista");
		model.addAttribute("title", "usuario.lista");
		model.addAttribute("paginacion", paginacion);
		getVistaUsuario( model, false);
		setModeloListado(model, "admin/usuarios", "/admin/newUser", "/admin/usuario","/home" );
		return model.getAttribute("viewName").toString();
	}
	/**
	 * Nuevo usuario desde el listado de usuarios de Administración.
	 * @param model Modelo para la vista.
	 * @return Vista de los datos
	 */
	@GetMapping("/admin/newUser")
	public String nuevo(Model model) {
        model.addAttribute("form", new UsuarioRegistroDTO());
		return getVistaUsuario(model, false);
	}
	/**
	 * Consulta/modificación de un usuario.
	 * 
	 * @param success Mensaje de "OK" que viene desde el Post.
	 * @param id Identificador del usuario a modificar.
	 * @param model Modelo para la vista.
	 * @return Vista que va a visualizar los datos.
	 */
	@GetMapping("/admin/usuario/{id}")
    public String modificar(@RequestParam(required = false) String success, 
    		@PathVariable Long id, 
    		Model model) {
		if (success != null) {
			model.addAttribute("success", "mensaje.grabacionOK");
		}
		model.addAttribute("form", userService.getUsuario(id));
		return getVistaUsuario(model, false);
	}
	
	/**
	 * Visualización de los datos de mi perfil.
	 * <p> Para ADMIN y PROFE ya que son usuarios base.
	 * 
	 * @param urlBase Diferenciación de si es admin o profesor.
	 * @param success Mensaje que viene del Post por grabación correcta.
	 * @param userConnected Usuario conectado para obtener su id.
	 * @param model 
	 * @return
	 */
	@GetMapping("/{urlBase}/miperfil")
	@PreAuthorize(
		    "(#urlBase == 'admin' and hasRole('ADMIN')) or " +
		    "(#urlBase == 'profesor' and hasRole('PROFE'))"
		)
	public String miPerfil(
			@PathVariable("urlBase") String urlBase,
			@RequestParam(required = false) String success,
			@AuthenticationPrincipal SecurityUser userConnected, 
			Model model) {
			model.addAttribute("form", userService.getUsuario(userConnected.getId()));
			model.addAttribute("success", success);
			setModeloFormulario(model, "admin/usuario", "/" + urlBase + "/miperfil", "/");
			model.addAttribute("isUser", true);
			return "admin/usuario";
	}
	/**
	 * Solicitud de cambio de password.
	 * @param model Modelo a rellenar para la vista.
	 * @return Vista que dibuja los datos.
	 */
	@GetMapping("/{urlBase}/miperfil/password/{id}")
	@PreAuthorize(
		    "(#urlBase == 'admin' and hasRole('ADMIN')) or " +
		    "(#urlBase == 'profesor' and hasRole('PROFE'))"
		)
	public String showPasswordForm(@AuthenticationPrincipal SecurityUser userConnected,
			@PathVariable("urlBase") String urlBase,
			@PathVariable Long id,
			Model model) {
		
		model.addAttribute("viewName", "comun/cambio-password");
		model.addAttribute("url", "/cambiopassword");
		model.addAttribute("urlCancel", "/" + urlBase + "/miperfil");
		model.addAttribute("isUser", true);
	    model.addAttribute("form", new UserPasswordChangeDTO(userConnected.getUsername()));
	    return "comun/cambio-password";
	}

	/**
	 * Grabación de los datos del perfi de Admin o Profesor.
	 * @param userConnected Usuario conectado para obtener su id.
	 * @param urlBase  Diferenciación de si es admin o profesor.
	 * @param form	Datos que vienen de la vista.
	 * @param result Binding para comprobación de datos.
	 * @param model
	 * @return
	 */
	@PostMapping("/{urlBase}/miperfil")
	@PreAuthorize(
		    "(#urlBase == 'admin' and hasRole('ADMIN')) or " +
		    "(#urlBase == 'profesor' and hasRole('PROFE'))"
		)
	public String guardarPerfil(@AuthenticationPrincipal SecurityUser userConnected, 
			@PathVariable("urlBase") String urlBase,
			@ModelAttribute("form") UsuarioRegistroDTO form,
            BindingResult result,
            Model model) {
		
		new SpringValidatorAdapter(validator)
        .validate(form, result, OnUpdate.class);
        if (result.hasErrors()) {
        	setModeloFormulario(model, "admin/usuario", "/" + urlBase + "/miperfil", "/");
        	
            return model.getAttribute("viewName").toString();
        }
        
        model.addAttribute("form", userService.grabar(form,userConnected.getUsername()));
        model.addAttribute("success", "mensaje.grabacionOK");
        
        return "redirect:/" + urlBase + "/miperfil?sucess";
	}
	/**
	 * Método para la edición de un usuario
	 * @param model Modelo a rellenar.
	 * @return Vista del usuario
	 */
	private String getVistaUsuario(Model model, boolean isUser) {
		setModeloFormulario(model, "admin/usuario", "/admin/usuario","/admin/usuario");
		model.addAttribute("isUser", isUser);
        model.addAttribute("roles", rolService.getList());
    	return model.getAttribute("viewName").toString();
    }
	/**
	 * Modificación de datos de usuario desde Administrador de usuarios.
	 * @param usuario Usuario que realiza la modificación/alta.
	 * @param form Datos del Usuario.
	 * @param result Binding con el resultado de las validaciones.
	 * @param model Modelo de datos a mostrar.
	 * @return "Redirección" al controlador origen.
	 */
	@PostMapping("/admin/usuario")
	public String grabarUsuario(@AuthenticationPrincipal UserDetails usuario,
			@ModelAttribute("form") UsuarioRegistroDTO form,
            BindingResult result,
            Model model) {

		  // 1) Para utilizar el mismo DTO  
		  //     Creación (con password) 
		  //     Modificación (sin password) y que  no salte el @NotBlank del validator
	    Class<?> grupo = (form.getId() == null) ? OnCreate.class : OnUpdate.class;

	    // 2) Validar dinámicamente según el grupo
	    new SpringValidatorAdapter(validator)
	            .validate(form, result, grupo);
	    
        if (result.hasErrors()) {
        	setModeloFormulario(model, "admin/usuario", "/admin/usuario","/admin/usuario");
        	model.addAttribute("roles", rolService.getList());
            return model.getAttribute("viewName").toString();
        }
        Long id = userService.grabar(form,usuario.getUsername()).getId();
        model.addAttribute("success", "mensaje.grabacionOK");
		return String.format("redirect:/admin/usuario/%s?success",id.toString());
	}
	/**
	 * Listado de usuarios contectados.
	 * 
	 * @param model Modelo a rellenar para la vista.
	 * @return Vista que dibuja los datos.
	 */
	@GetMapping("/admin/usuarioconnected")
    public String modUsuario(Model model) {
		model.addAttribute("usuarios", userService.getConnectedUsers());
		model.addAttribute("urlBack", "/home");
		return "admin/usuariosconectados";
	}
	/**
	 * Grabación de cambio de password.
	 * @param model Modelo a rellenar para la vista.
	 * @return Vista que dibuja los datos.
	 */
	@PostMapping("/cambiopassword")
	public String changePassword(@AuthenticationPrincipal UserDetails userDetails,
	                             @Valid @ModelAttribute("form") UserPasswordChangeDTO dto,
	                             BindingResult result,
	                             HttpServletRequest request,
	                             Model model) {
		request.setAttribute("viewName", "comun/cambio-password");
		model.addAttribute("viewName", "comun/cambio-password");
	    if (result.hasErrors()) {
	    	model.addAttribute("isUser", true);
	    	return "comun/cambio-password";
	    }
	    userService.cambioPassword(dto, userDetails.getUsername());
	    model.addAttribute("success", "{password.change.success}");
	    return "comun/cambio-password";
	}
	/**
	 * Modificación de password desde Administración de usuarios.
	 * @param id Id del usuario a modificar contraseña.
	 * @param success Mensaje "success" de realizado.
	 * @param model Modelo para visualizar los datos.
	 * @return Vista para mostrar el mensaje.
	 */
	@GetMapping("/admin/usuario/password/{id}")
	public String showAdminPasswordForm(@PathVariable Long id, 
			@RequestParam(required = false) String success,
			Model model) {
	    model.addAttribute("userId", id);
	    model.addAttribute("form", new UserPasswordAdminChangeDTO(userService.getUsuario(id).getEmail()));
	    if (success != null) {
	        model.addAttribute("success", "password.change.success");
	    }
	    model.addAttribute("isUser", false);
	    model.addAttribute("url", String.format("/admin/usuario/password/%d", id));
	    model.addAttribute("urlCancel", String.format("/admin/usuario/%d", id));
	    return "comun/cambio-password";
	}

	/**
	 * Grabación de la modificación de la contraseña.
	 * 
	 * @param id Identificador del usuario a modificar.
	 * @param dto Dto que contiene los datos.
	 * @param result Resultado de la operación.
	 * @param model Modelo para contener los datos.
 	 * @return Vista que muestra los datos organizados.
	 */
	@PostMapping("/admin/usuario/password/{id}")
	public String changeUserPassword(@PathVariable Long id,
	                                 @Valid @ModelAttribute("form") UserPasswordAdminChangeDTO dto,
	                                 BindingResult result,
	                                 Model model) {

		
	    if (!result.hasErrors()) {
	    	model.addAttribute("success", "password.change.success");
	    }
	    userService.cambioPasswordUserAdmin(dto, id);
	    model.addAttribute("userId", id);
	    model.addAttribute("url", "admin/usuario");
	    model.addAttribute("urlCancel", String.format("/admin/usuario/%d", id));
	    return "comun/cambio-password";
	}

}
