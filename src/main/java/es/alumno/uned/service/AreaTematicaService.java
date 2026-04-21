package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;

import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.util.PaginacionComun;

public interface AreaTematicaService {

	PaginacionComun<AreaTematica> listadoPaginado(String string, Pageable pageRequest);

	@Nullable
	Object getAreaTematica(Long id);

}
