package es.alumno.uned.exception;

import es.alumno.uned.dto.ModuloDTO;
/**
 * Excepción controlada cuando un módulo ya existe. Normalmente en altas.
 */
public final class ModuloAlreadyExistException extends AlreadyExistException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModuloAlreadyExistException(String messageKey, ModuloDTO dto, Object... args) {
        super(messageKey, dto, args);
    }
}

