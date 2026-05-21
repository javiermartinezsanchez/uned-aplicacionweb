package es.alumno.uned.service;


import java.util.Map;

import org.jspecify.annotations.Nullable;

import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.Paginacion;

/**
 * Interface de métodos de Estudiante.
 */
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

	/**
	 * Consulta paginada de Estudiantes
	 * @param params Mapa de parámetros para la búsqueda.
	 * @param pageData Datos de la página.
	 * @return Página de datos obtenida.
	 */
	public Paginacion<Estudiante, EstudianteDTO> listadoPaginado( Map<String, String> params, PageParams pageData);
	

}
