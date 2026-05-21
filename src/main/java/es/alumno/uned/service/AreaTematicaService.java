package es.alumno.uned.service;

import java.util.List;
import java.util.Map;

import org.jspecify.annotations.Nullable;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.Paginacion;
/**
 *  	Interface público de los métodos públicos con acceso a Areas Temáticas.
 */
public interface AreaTematicaService {

	/**
	 * Listado paginado de las Areas temáticas.
	 * @param params Mapa de parámetros para el filtrado de la búsqueda.  
	 * @param pageRequest Definición de la página.
	 * @return Página de los datos solicitados.
	 */
	Paginacion<AreaTematica, AreaTematicaDTO> listadoPaginado( Map<String, String> params, PageParams pageRequest);

	/**
	 * Consulta de un Area Temática por su Id
	 * @param id Id del Área temática a buscar.
	 * @return el Area Temática buscado o "null" si no lo ha encontrado nada.
	 */
	@Nullable
	Object getAreaTematica(Long id);
	
	/**
	 * Guardamos los datos del Área Temática nueva/modificada
	 * @param area Los datos del Área
	 * @return Los nuevos datos modificados
	 */
	AreaTematicaDTO grabar(AreaTematicaDTO area);
	
	/**
	 * Nos devuelve todas las areas temáticas existentes convertidas en su DTO (para combos y otros usos)
	 * @return Lista de las áreas
	 */
	List<AreaTematicaDTO> listAll();

	/**
	 * Búsqueda de Áreas Temáticas por un "array" de id's.
	 * @param areasIds Listado de Id a buscar.
	 * @return Devolvemos el AreaTematicaDTO para su uso.
	 */
	List<AreaTematicaDTO> findAllById(List<Long> areasIds);
}
