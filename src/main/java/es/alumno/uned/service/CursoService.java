package es.alumno.uned.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.CursoDTO;

public interface CursoService {

	void nuevoCurso(CursoDTO dto, MultipartFile imagen, String usuario) throws IOException;

}
