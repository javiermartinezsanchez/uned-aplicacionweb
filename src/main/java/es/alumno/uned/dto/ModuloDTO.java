package es.alumno.uned.dto;

import es.alumno.uned.model.entities.TipoModulo;
import jakarta.validation.constraints.*;

public record ModuloDTO(

        Long id,

        @NotNull(message = "El curso es obligatorio")
        Long cursoId,

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 100, message = "El título no puede superar los 100 caracteres")
        String titulo,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
        String descripcion,

        @NotBlank(message = "El contenido es obligatorio")
        String contenido,

        @NotNull(message = "El peso es obligatorio")
        @Min(value = 1, message = "El peso mínimo es 1")
        @Max(value = 100, message = "El peso máximo es 100")
        Integer peso,

        @NotNull(message = "El tipo de módulo es obligatorio")
        TipoModulo tipo,

        @NotNull(message = "Indique si requiere entrega")
        Boolean requiereEntrega,

        @NotNull(message = "Indique si la finalización es automática")
        Boolean finalizacionAutomatica

) {}