package es.alumno.uned.exception;

import es.alumno.uned.dto.AreaTematicaDTO;
/**
 * Excepción en el alta de un Área Temática
 */
public class AreaTematicaAlreadyExistException extends AlreadyExistException {
	    public AreaTematicaAlreadyExistException(String messageKey, AreaTematicaDTO dto, Object... args) {
	        super(messageKey, dto, args);
	    }
}


