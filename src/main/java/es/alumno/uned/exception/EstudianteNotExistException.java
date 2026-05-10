package es.alumno.uned.exception;

/** 
 * Excepción de que un Estudiante no existe.
 */
public final class EstudianteNotExistException extends NotDataFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8630076983422828244L;

	public EstudianteNotExistException(String messageKey, Object dto, String...args ) {
		super(messageKey, dto, args);
	}

}
