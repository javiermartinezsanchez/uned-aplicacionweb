package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.ContenidoExtraDTO;

@Service
public class ContenidoExtraServiceImpl implements ContenidoExtraService {

	@Autowired
	FileStorageService fileStorageService;
	
	@Override
	public ContenidoExtraDTO getContenido(Long idCurso, Long idContenido) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Resource getResource(String uri) {
		Resource resource = fileStorageService.getDocumento(uri);
		return resource;
	}

}
