package es.alumno.uned.dto;

import java.time.LocalDateTime;

public record EstudianteCursoModuloDTO(
        Long estudianteId,
        Long cursoId,
        Long moduloId,
        Boolean completado,
        LocalDateTime fechaCompletado,
        LocalDateTime fechaUltimoAcceso,
        Double calificacion
) {}

