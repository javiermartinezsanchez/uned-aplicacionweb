package es.alumno.uned.service;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.util.Paginacion;

public interface CursoService {

	void nuevoCurso(CursoDTO dto, MultipartFile imagen, String usuario) throws IOException;

	public Paginacion<Curso, CursoDTO> listadoPaginado(String url,  Pageable pageable);
}
