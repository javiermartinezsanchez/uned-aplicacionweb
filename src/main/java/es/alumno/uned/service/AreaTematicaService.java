package es.alumno.uned.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.util.Paginacion;
/**
 *  	Interface público de los métodos 
 */
public interface AreaTematicaService {

	/**
	 * Listado paginado de las Areas temáticas.
	 * @param urlDetalle String con  
	 * @param pageRequest Definición de la página.
	 * @return Página de los datos solicitados.
	 */
	Paginacion<AreaTematica, AreaTematicaDTO> listadoPaginado(String urlDetalle, String titulo, String descripcion, Pageable pageRequest);

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
}
