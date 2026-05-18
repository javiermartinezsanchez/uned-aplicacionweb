package es.alumno.uned.model.records;

import es.alumno.uned.model.entities.TipoFichero;

/**
 * Estructura para la administración de espacio.
 * <p>Identificamos el nombre, tamaño, el Tipo y si es huérfano (ha perdido su relacióno con nuestras entidades)
 */
public record ArchivoFisicoDTO(
	    String nombre,
	    String nombreReal,
	    String sizeKB,
	    boolean esHuerfano,
	    TipoFichero tipoFichero
	) {}
