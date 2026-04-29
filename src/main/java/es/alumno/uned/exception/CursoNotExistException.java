package es.alumno.uned.exception;

public class CursoNotExistException extends RuntimeException{

	public CursoNotExistException(final String message) {
		super(message);
	}
}
