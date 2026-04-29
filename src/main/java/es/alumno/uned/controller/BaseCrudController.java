package es.alumno.uned.controller;

import org.springframework.ui.Model;

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
	 * Define en el modelo {@link Model} los valores de la vista y las URL necesarias en cada formulario.
	 * @param model Modelo de la vista.
	 * @param viewName Vista a mostrar.
	 * @param urlGuardar Url para el comando de "Guardar" 
	 * @param urlCancelar Url para el comando "Cancelar"
	 */
    protected void prepararModeloFormulario(
            Model model,
            String viewName,
            String urlGuardar,
            String urlCancelar
    ) {
        model.addAttribute("viewName", viewName);
        model.addAttribute("url", urlGuardar);
        model.addAttribute("urlCancel", urlCancelar);
    }
}

