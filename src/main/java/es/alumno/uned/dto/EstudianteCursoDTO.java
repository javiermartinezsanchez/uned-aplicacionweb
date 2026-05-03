package es.alumno.uned.dto;

import java.time.LocalDateTime;

import es.alumno.uned.model.entities.EstadoCurso;

public record EstudianteCursoDTO(
        Long estudianteId,
        Long cursoId,
        LocalDateTime fechaSubscripcion,
        LocalDateTime fechaUltimoAcceso,
        Integer progreso,
        Double calificacionFinal,
        EstadoCurso estado
) {}




