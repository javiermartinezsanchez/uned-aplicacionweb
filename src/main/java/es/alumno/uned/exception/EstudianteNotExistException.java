package es.alumno.uned.exception;

/** 
 * Excepción de que un Estudiante no existe.
 */
public class EstudianteNotExistException extends NotDataFoundException{

	public EstudianteNotExistException(String messageKey, Object dto, String...args ) {
		super(messageKey, dto, args);
	}

}
