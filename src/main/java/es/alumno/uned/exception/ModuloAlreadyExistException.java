package es.alumno.uned.exception;

import es.alumno.uned.dto.ModuloCursoDTO;

public class ModuloAlreadyExistException extends AlreadyExistException {
    public ModuloAlreadyExistException(String messageKey, ModuloCursoDTO dto, Object... args) {
        super(messageKey, dto, args);
    }
}

