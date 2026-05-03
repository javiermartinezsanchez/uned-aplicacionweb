package es.alumno.uned.exception;

import es.alumno.uned.dto.ModuloDTO;

public class ModuloAlreadyExistException extends AlreadyExistException {
    public ModuloAlreadyExistException(String messageKey, ModuloDTO dto, Object... args) {
        super(messageKey, dto, args);
    }
}

