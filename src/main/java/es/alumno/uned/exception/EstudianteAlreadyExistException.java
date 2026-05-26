package es.alumno.uned.exception;

/**
 * Excepción que generamos cuando un Estudiante ya exista en procesos de alta o modificación.
 */
public final class EstudianteAlreadyExistException extends AlreadyExistException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EstudianteAlreadyExistException(String messageKey, Object dto, Object... args) {
        super(messageKey, dto, args);
    }
}

