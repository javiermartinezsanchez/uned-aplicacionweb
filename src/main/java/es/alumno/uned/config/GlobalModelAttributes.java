package es.alumno.uned.config;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.service.AreaTematicaService;
import jakarta.servlet.http.HttpServletRequest;
/**
 * Clase {@code @ControllerAdvice} para mantener ciertos atributos del modelo visible por todos los controladores.
 * 
 * 
 * 
 */
@ControllerAdvice
public class GlobalModelAttributes {

	@Autowired
    private AreaTematicaService areaTematicaService;

	/**
	 * <p>Inyecta en el modelo una variable de tipo texto (String) llamada currentUri que contiene la ruta exacta de la petición HTTP actual, excluyendo el dominio y los parámetros de consulta (query strings).
	 * 
	 * <p>Recibe el objeto HttpServletRequest de la petición en curso y extrae el Path mediante el método getRequestURI()
	 * @param request HttpRequest realizado.
	 * @return URI de la petición (request).
	 */
	@ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
    
	/**
	 * Inyecta en el modelo un objeto constructor de URLs (ServletUriComponentsBuilder) guardado bajo la variable urlBuilder. 
	 * <p>Este objeto viene precargado con todos los datos de la petición HTTP actual (protocolo, dominio, puerto, ruta y parámetros).
	 * <p>Utiliza el contexto estático de Spring Web para capturar la petición activa (fromCurrentRequest()) y expone esta factoría dinámica directamente a la vista.
	 * <p><b>Utilidad en las vistas (Thymeleaf/JSP):</b>
	 * <p>Nos sirve para crear enlaces de paginación o filtros dinámicos.
	 * <p>Permite a la vista modificar o añadir parámetros a la URL actual sin perder los que ya existían.
	 * <p><b>Ejemplo en Thymeleaf:</b> Si estás en /cursos?buscar=java y quieres cambiar a la página 2, en el HTML puedes invocar al objeto de esta forma: {@code ${urlBuilder.replaceQueryParam('page', 2).toUriString()}}, lo que generará automáticamente /cursos?buscar=java&page=2.
	 * @return
	 */
    @ModelAttribute("urlBuilder")
    public ServletUriComponentsBuilder urlBuilder() {
        // Retorna un constructor basado en la petición actual
        return ServletUriComponentsBuilder.fromCurrentRequest();
    }
    
    /**
     * Como el buscador de la home, está en el fragmento "header", es
     * común a todo el proyecto (excepto si hubiera alguna plantilla sin el header).
     * <p>En las páginas con buscador que generan un lista de elementos, al seleccionar una y volver al mismo, "necesitamos" recuperar los datos de dicho buscador.
     * <p>Incluimos todos los parámetros en un {@code Map<String, String>}, disponible por todos los @Controller y las vistas asociadas.
     * <p>Spring lo maneja a nivel de {@link ModelAttribute} 
     * @param allParams Captura todos los parámetros de la query y lo devuelve, 
     * de forma que siempre existirá, aunque sea vacio.
     * @return Map con todos los parámetros o {} (vacio).
     */
    @ModelAttribute("paramsBusqueda")
    public Map<String, String> getParamsBusqueda(@RequestParam Map<String, String> allParams) {
         return allParams; 
    }
    
    /**
     * Injectamos la lista de áreas en todos los "Model".
     * <p>Permite tener accesible esta lista para el Buscador general de la "Home" y para otros lugares que sea necesario.
     * <p><b>NOTA:</b>
     * <p>En los casos que existan dos listas en una misma página, aunque estén en formularios diferentes, no se podrá utilizar el {@code getElementById()} ya que encontrará varios elementos con el mismo.
     * 
     * @return Lista de áreas con el nombre "areas".
     */
    @ModelAttribute("areas")
    public List<AreaTematicaDTO> getAreas() {
        return ordenadaPorTitulo(areaTematicaService.listAll());
    }

    private List<AreaTematicaDTO> ordenadaPorTitulo(List<AreaTematicaDTO> listaAreas){
    	listaAreas.sort(Comparator.comparing(AreaTematicaDTO :: getTitulo));
    	return listaAreas;
    }
}
