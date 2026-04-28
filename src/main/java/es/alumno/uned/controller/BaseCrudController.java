package es.alumno.uned.controller;

import org.springframework.ui.Model;

/**
 * Clase abstracta y común a todos los controladores con la arquitectura
 * elegida.
 * 
 * <p>Se extenderá de cualquier {@code @Controller} y nos permitirá definir 
 * los datos comunes a todos los métodos (GET y POST)
 * 
 * <p>También nos permite, en casos de excepciones reconstruir el modelo de ç
 * forma correcta mediante las excepciones definidas.
 */
public abstract class BaseCrudController {

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

