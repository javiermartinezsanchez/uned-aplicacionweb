package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;

import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.model.entities.Estudiante;

public interface EstudianteService {

   /**
    * Guardamos la información del Estudiante.
    * 
    * @param estudiante DTO con la información del Estudiante
    * @param usuario UserName (email), del usuario que genera el proceso (para altas)
    */
	void guardar(EstudianteDTO estudiante, String usuario);

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

	
	//Optional<Estudiante> findByUsuarioEmail(String email);
}
