package es.alumno.uned.exception;

import es.alumno.uned.dto.AreaTematicaDTO;

public final class UserAlreadyExistException extends AlreadyExistException {

    private static final long serialVersionUID = 5861310537366287163L;

    public UserAlreadyExistException(String messageKey, AreaTematicaDTO dto, Object... args) {
        super(messageKey, dto, args);
    }
}
