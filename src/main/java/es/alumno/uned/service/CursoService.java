package es.alumno.uned.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.util.Paginacion;

public interface CursoService {

	/**
	 * Devolvemos un curso según del Id enviado
	 * @param id Id del curso
	 * @return DTO del curso
	 */
	public CursoDTO getCurso(Long id);
	
	CursoDTO grabar(CursoDTO dto, MultipartFile imagen, String usuario) throws IOException;

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
    
    List<CursoDTO> listadoHome();
	
    /**
     * Proceso de guardado de una valoración de un curso
     * @param cursoId Identificador del curso que se valora.
     * @param valoracion Valoración (1-5)
     * @param usuario Nombre de usuario que valora.
     * @return Se devuelve la media de valoración del curso
     */
    BigDecimal guardarValoracion(Long cursoId, Integer valoracion, String usuario);
}
