package es.alumno.uned.model.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoContenido {
    PROPIO, EXTERNO;
	
	@JsonCreator
    public static TipoContenido fromString(String value) {
        if (value == null) return null;
        return TipoContenido.valueOf(value.toUpperCase());
    }
}
