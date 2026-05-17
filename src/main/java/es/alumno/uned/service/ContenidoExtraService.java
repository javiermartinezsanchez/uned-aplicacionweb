package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.Resource;

import es.alumno.uned.dto.ContenidoExtraDTO;

public interface ContenidoExtraService {

	ContenidoExtraDTO getContenido(Long idCurso, Long idContenido);

	@Nullable
	Resource getResource(String uri);
}
