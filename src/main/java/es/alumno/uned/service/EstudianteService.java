package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;

import es.alumno.uned.dto.PerfilEstudianteDTO;
import es.alumno.uned.model.entities.Estudiante;

public interface EstudianteService {

   /**
    * Guardamos la información del Estudiante.
    * 
    * @param estudiante DTO con la información del Estudiante
    * @param usuario UserName (email), del usuario que genera el proceso (para altas)
    */
	void guardar(PerfilEstudianteDTO estudiante, String usuario);

	/**
	 * Búsqueda de Estudiantes por su Id.
	 * 
	 * @param id Id del Estudiante
	 * @return Estudiante encontrado.
	 */
	
	Estudiante findById(Long id);

	/**
	 * Devuelve el listado de Estudiantes
	 * @return
	 */
	@Nullable
	Object findAll();

}
