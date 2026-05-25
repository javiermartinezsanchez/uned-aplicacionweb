package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.ContenidoExtraDTO;
import es.alumno.uned.exception.ContenidoExtraNotFoundException;
import es.alumno.uned.mapper.ContenidoExtraMapper;
import es.alumno.uned.model.entities.ContenidoExtra;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.repository.CursoRepository;

@Service
public class ContenidoExtraServiceImpl implements ContenidoExtraService {

	@Autowired
	CursoRepository cursoRepository;
	@Autowired
	ContenidoExtraMapper contenidoExtraMapper;
	@Autowired
	FileStorageService fileStorageService;
	
	@Override
	public ContenidoExtraDTO getContenido(Long idCurso, Long idContenido) throws ContenidoExtraNotFoundException {
		Curso curso = cursoRepository.findById(idCurso).get();
		ContenidoExtra ce = curso.getContenidosExtra()
				.stream()
				.filter(c -> c.getId() == idContenido)
		.findFirst().orElse(null);
		if (ce == null) {
			throw new ContenidoExtraNotFoundException("Contenido no encontrado");
		}
		
		return contenidoExtraMapper.toDTO(ce);
	}

	@Override
	public @Nullable Resource getResource(String uri) {
		Resource resource = fileStorageService.getDocumento(uri);
		return resource;
	}

}
