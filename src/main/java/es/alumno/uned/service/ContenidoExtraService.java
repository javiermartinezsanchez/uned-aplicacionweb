package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.Resource;

import es.alumno.uned.dto.ContenidoExtraDTO;
import es.alumno.uned.exception.ContenidoExtraNotFoundException;

public interface ContenidoExtraService {

	ContenidoExtraDTO getContenido(Long idCurso, Long idContenido) throws ContenidoExtraNotFoundException;

	@Nullable
	Resource getResource(String uri);
}
