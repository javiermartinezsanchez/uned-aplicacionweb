package es.alumno.uned.model.records;

public record FicheroData(
		int indice,
	    String nombreOriginal,
	    String contentType,
	    byte[] contenido
	) {}
