package es.alumno.uned.exception;
/**
 * Excepción que generaremos cuando un Curso no exista, o el usuario que la intenta visualizar no esté autorizado.
 */
public final class CursoNotExistException extends NotDataFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 22321534353560871L;

	public CursoNotExistException(String messageKey, Object dto, String ... args) {
		super(messageKey, dto, args);
	}
}
