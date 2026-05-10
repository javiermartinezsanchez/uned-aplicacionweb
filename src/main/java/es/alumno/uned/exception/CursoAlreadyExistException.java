package es.alumno.uned.exception;

import es.alumno.uned.dto.CursoDTO;
/**
 * Excepción controlada cuando un Curso ya existe. Normalmente en altas.
 */
public final class CursoAlreadyExistException extends AlreadyExistException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3126007241237083051L;

	public CursoAlreadyExistException(String messageKey, CursoDTO dto, Object... args) {
        super(messageKey, dto, args);
    }
}

