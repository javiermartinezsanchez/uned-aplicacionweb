package es.alumno.uned.exception;

import es.alumno.uned.dto.EstudianteDTO;

public class EstudianteAlreadyExistException extends AlreadyExistException {
    public EstudianteAlreadyExistException(String messageKey, EstudianteDTO dto, Object... args) {
        super(messageKey, dto, args);
    }
}

