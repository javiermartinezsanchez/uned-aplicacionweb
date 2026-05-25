package es.alumno.uned.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;

import es.alumno.uned.model.records.PageParams;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase abstracta y común a todos los controladores con la arquitectura
 * elegida.
 * 
 * <p>Se extenderá de cualquier {@code @Controller} y nos permitirá definir 
 * los datos comunes a todos los métodos (GET y POST)
 * 
 * <p>También nos permite, en casos de excepciones reconstruir el modelo de
 * forma correcta mediante las excepciones definidas.
 */
public abstract class BaseCrudController {

	/**
	 * Injectamos el "messageSource" para los textos.
	 */
	@Autowired
    private MessageSource messageSource;
	/**
	 * Para las rutas compartidas, definimos la url de vuelta o de Cancel.
	 * 
	 * @param urlBack Ruta por defecto.
	 * @param rol Rol del usuario.
	 * @return UrlBack generada.
	 */
	protected String getUrlBack(String urlBack, String rol) {
		if (rol.equals("ADMIN")) {
	        urlBack = "/admin".concat(urlBack);
	    } else if (rol.equals("PROFE")) {
	        urlBack = "/profesor".concat(urlBack);
	    }		
		return urlBack;
	}
	/**
	 * Define en el {@link Model} la vista y las URL necesarias en cada formulario.
	 * @param model Modelo de la vista.
	 * @param viewName Vista a mostrar.
	 * @param urlGuardar Url para el comando de "Guardar" 
	 * @param urlCancelar Url para el comando "Cancelar"
	 */
    protected void setModeloFormulario(
            Model model,
            String viewName,
            String urlGuardar,
            String urlCancelar
    ) {
        model.addAttribute("viewName", viewName);
        model.addAttribute("url", urlGuardar);
        model.addAttribute("urlCancel", urlCancelar);
    }
	/**
	 * Define en el {@link HttpServletRequest} la vista y las URL necesarias en cada formulario para capturarlo en casos de error.
	 * 
	 * <p>Se captura en el {@code @ControlAdvice}, para definir errores controlados sin tener que gestionarlos en los Controllers.
	 * @param request HttpRequest que llega al método del Controller.
	 * @param viewName Vista a mostrar.
	 * @param urlGuardar Url para el comando de "Guardar" 
	 * @param urlCancelar Url para el comando "Cancelar"
	 * @param urlRedirect Url de redirección en caso de exito.
	 */
    protected void setRequestFormulario(
    		HttpServletRequest request,
            String viewName,
            String urlGuardar,
            String urlCancelar,
            String urlRedirect
    ) {
    	request.setAttribute("viewName", viewName);
    	request.setAttribute("url", urlGuardar);
    	request.setAttribute("urlCancel", urlCancelar);
    	request.setAttribute("urlRedirect", urlRedirect);
    }
    /**
     * Define en el {@link Model} la vista y las url comunes a todos los listados.
     * @param model Modelo de la vista.
     * @param viewName Vista a mostrar.
     * @param urlAlta Url para un nuevo registro
     * @param urlDetalle Url para el comando de ver/modificar
     * @param urlBack Url para el comando "volver"
     */
    protected void setModeloListado(Model model, String viewName, 
    		String urlAlta,
    		String urlDetalle, 
    		String urlBack) {
        model.addAttribute("urlAlta", urlAlta);
        model.addAttribute("viewName", viewName);
        model.addAttribute("url", urlDetalle);
        model.addAttribute("urlBack", urlBack);
    }
    
    /**
     * Definición del tamaño de la página de datos por defecto.
     * 
     * @return El tamaño de la página de datos.
     */
    protected int getPageSize() {
        return 10; 
    }

    /**
     * Se genera un objeto {@link PageParams} encapsulando los datos para la paginación.
     * 
     * @param page
     * @return Parámetros de paginación con el "default" de PageSize.
     */
    protected PageParams getParams(int page) {
        return new PageParams(page, getPageSize());
    }
    /**
     * Se genera un objeto {@link PageParams} encapsulando los datos para la paginación.
     * 
     * @param page Número de página
     * @param size Tamaño de la página
     * @return Parámetros de paginación con el "default" de PageSize.
     */
    protected PageParams getParams(int page, int size) {
        return new PageParams(page, size);
    }
	/**
	 * Limpia el mapa de parametros que llegan desde el formulario.
	 * <p>Genera un nuevo mapa de valores:
	 * <ul>
	 * <li>Elimina los parámetro sin valor, o con espacios en blanco </li>
	 * <li>Elimina el parámetro "page".</li>
	 * </ul>
	 * <p>Nos sirve para mantener los campos de búsqueda en el modelo y no tener que generar "n" variables.
	 * 
	 * <p>El "Service" lo recibirá y nos devuele la consulta adecuada a los mismos.
	 * @param params Mapa de parámetros recibidos por el controller.
	 * @return Mapa de clave-valor sin "page" y sin los parámetros vacios.
	 */
	public Map<String, String> paramsToMap(Map<String, String> params){
		Map<String, String> filtros = 
				params == null? new HashMap<>(Map.of("","")) :
				new HashMap<>(params);
		filtros.remove("page");
		filtros.values().removeIf(v -> v == null || v.isBlank());
		return filtros;
	}

}

