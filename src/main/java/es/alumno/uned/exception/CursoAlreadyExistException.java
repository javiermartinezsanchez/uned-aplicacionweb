package es.alumno.uned.exception;

import es.alumno.uned.dto.CursoDTO;

public class CursoAlreadyExistException extends AlreadyExistException {
    public CursoAlreadyExistException(String messageKey, CursoDTO dto, Object... args) {
        super(messageKey, dto, args);
    }
}

