package es.alumno.uned.controller;

import org.springframework.ui.Model;

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
	 */
    protected void setRequestFormulario(
    		HttpServletRequest request,
            String viewName,
            String urlGuardar,
            String urlCancelar
    ) {
    	request.setAttribute("viewName", viewName);
    	request.setAttribute("url", urlGuardar);
    	request.setAttribute("urlCancel", urlCancelar);
    }
    /**
     * Define en el {@link Model} la vista y las url comunes a todos los listados.
     * <p>Se añade la query para mantener los campos de búsqueda (si existen) en la paginación.
     * @param model Modelo de la vista.
     * @param viewName Vista a mostrar.
     * @param urlAlta Url para un nuevo registro
     * @param urlDetalle Url para el comando de ver/modificar
     * @param urlBack Url para el comando "volver"
     * @param query Query con los parámetro de búsqueda para la paginación.
     */
    protected void setModeloListado(Model model, String viewName, 
    		String urlAlta,
    		String urlDetalle, 
    		String urlBack, 
    		String query) {
        model.addAttribute("urlAlta", urlAlta);
        model.addAttribute("viewName", viewName);
        model.addAttribute("url", urlDetalle);
        model.addAttribute("urlBack", urlBack);
        model.addAttribute("query", query);
    }
}

