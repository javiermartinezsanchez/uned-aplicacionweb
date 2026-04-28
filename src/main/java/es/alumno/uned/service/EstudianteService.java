package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;

import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.util.Paginacion;

public interface EstudianteService {

   /**
    * Guardamos la información del Estudiante.
    * 
    * @param estudiante DTO con la información del Estudiante
    * @param usuario UserName (email), del usuario que genera el proceso (para altas)
    */
	void grabar(EstudianteDTO estudiante, String usuario);

	/**
	 * Búsqueda de Estudiantes por su Id.
	 * 
	 * @param id Id del Estudiante
	 * @return Estudiante encontrado (DTO).
	 */
	
	EstudianteDTO findById(Long id);

	/**
	 * Devuelve el listado de Estudiantes
	 * @return
	 */
	@Nullable
	Object findAll();

	public Paginacion<Estudiante, EstudianteDTO> listadoPaginado(String url, Pageable pageRequest);
	//Optional<Estudiante> findByUsuarioEmail(String email);
}
