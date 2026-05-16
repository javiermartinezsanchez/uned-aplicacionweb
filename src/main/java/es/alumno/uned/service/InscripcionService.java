package es.alumno.uned.service;


/**
 * Interface del servicio de Inscripción
 */
public interface InscripcionService {

	/**
	 * Proceso de subscripción de un estudiante a un curso.
	 * 
	 * @param cursoId Curso Id que se quiere inscribir.
	 * @param username Usuario (estudiante) que solicita la inscripción.
	 * 
	 */
	void suscribirEstudiante(Long cursoId, String username);

}