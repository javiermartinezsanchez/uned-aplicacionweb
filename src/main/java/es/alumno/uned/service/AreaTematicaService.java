package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.util.PaginacionComun;

public interface AreaTematicaService {

	PaginacionComun<AreaTematica> listadoPaginado(String string, Pageable pageRequest);

	@Nullable
	Object getAreaTematica(Long id);

	/**
	 * Nos genera una nueva Área Temática ni no existe
	 * @param area DTO con la información de la nueva Área Temática
	 * @return La nueva Área  temática.
	 */
	AreaTematicaDTO nuevaArea(AreaTematicaDTO area);
	
	/**
	 * Guardamos los datos del Área Temática modificada
	 * @param area Los datos del Área
	 * @return Los nuevos datos modificados
	 */
	AreaTematicaDTO grabar(AreaTematicaDTO area);
}
