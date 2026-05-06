package es.alumno.uned.exception;

public class CursoNotExistException extends NotDataFoundException{

	public CursoNotExistException(String messageKey, Object dto, String ... args) {
		super(messageKey, dto, args);
	}
}
