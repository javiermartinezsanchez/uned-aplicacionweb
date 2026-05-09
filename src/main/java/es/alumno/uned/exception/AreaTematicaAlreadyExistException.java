package es.alumno.uned.exception;

import es.alumno.uned.dto.AreaTematicaDTO;
/**
 * Excepción en el alta de un Área Temática
 */
public class AreaTematicaAlreadyExistException extends AlreadyExistException {
	    /**
	 * 
	 */
	private static final long serialVersionUID = 8497896469588092673L;

		public AreaTematicaAlreadyExistException(String messageKey, AreaTematicaDTO dto, Object... args) {
	        super(messageKey, dto, args);
	    }
}


