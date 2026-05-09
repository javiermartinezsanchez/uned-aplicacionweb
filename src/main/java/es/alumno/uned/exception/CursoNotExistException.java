package es.alumno.uned.exception;

public class CursoNotExistException extends NotDataFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 22321534353560871L;

	public CursoNotExistException(String messageKey, Object dto, String ... args) {
		super(messageKey, dto, args);
	}
}
