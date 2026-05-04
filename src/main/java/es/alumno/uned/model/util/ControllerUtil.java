package es.alumno.uned.model.util;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.util.UriUtils;

/**
 * Clase helper de procesos comunes a los controladores.
 */
public class ControllerUtil {

	/**
	 * Limpia el mapa de parametros que llegan desde el formulario.
	 * <p>Genera un nuevo mapa de valores:
	 * <ul>
	 * <li>Elimina los parámetro sin valor </li>
	 * <li>Elimina el parámetro "page".</li>
	 * </ul>
	 * <p>Nos sirve para mantener los campos de búsqueda en el modelo y no tener que generar "n" variables.
	 * 
	 * <p>El "Service" lo recibirá y nos devuele la consulta adecuada a los mismos.
	 * @param params Mapa de parámetros recibidos por el controller.
	 * @return Mapa de clave-valor sin "page" y sin los parámetros vacios.
	 */
	public static Map<String, String> paramsToMap(Map<String, String> params){
		Map<String, String> filtros = new HashMap<>(params);
		filtros.remove("page");
		filtros.values().removeIf(v -> v == null || v.isBlank());
		
		return filtros;
	}
	/**
	 * Genera una queryString tipo &amp;clave=valor&amp;clave1=valor1 de acuerdo al mapa
	 * de filtros que recibe.
	 * <p>Necesario para mantener los campos de búsqueda en la paginación.
	 * @param filtros Mapa de filtros.
	 * @return Cadena en formato QueryString
	 */
	public static String mapToQuery(Map<String, String> filtros) {
		return filtros.entrySet().stream()
				.filter(e -> !e.getKey().equals("page"))
	            .map(e -> e.getKey() + "=" +
	                    UriUtils.encode(e.getValue(), StandardCharsets.UTF_8))
	            .collect(Collectors.joining("&"));
	}
}
