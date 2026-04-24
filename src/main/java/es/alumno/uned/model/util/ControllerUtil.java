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
	 * Nos genera un mapa de valores de acuerdo al mapa de parámetros recibidos.
	 * Elimina el parámetro "page" para no duplicarlo.
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
	 * Genera una queryString tipo &clave=valor&clave1=valor1 de acuerdo al mapa
	 * de filtros que recibe.
	 * 
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
