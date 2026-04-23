package es.alumno.uned.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.util.Paginacion;

public interface CursoService {

	void nuevoCurso(CursoDTO dto, MultipartFile imagen, String usuario) throws IOException;

	public Paginacion<Curso, CursoDTO> listadoPaginado(String url,  Pageable pageable);
	
	Paginacion<Curso, CursoDTO> listadoPaginadoPorResponsable(
            String url, Pageable pageable, Long responsableId);

    Paginacion<Curso, CursoDTO> listadoPaginadoPorArea(
            String url, Pageable pageable, Long areaId);

    Paginacion<Curso, CursoDTO> listadoPaginadoPorNivel(
            String url, Pageable pageable, int nivel);

    Paginacion<Curso, CursoDTO> listadoPaginadoPorTitulo(
            String url, Pageable pageable, String titulo);

    Paginacion<Curso, CursoDTO> listadoPaginadoPorFechaInicio(
            String url, Pageable pageable, LocalDateTime desde, LocalDateTime hasta);
	
}
