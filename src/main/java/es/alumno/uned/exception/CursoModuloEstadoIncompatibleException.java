package es.alumno.uned.exception;
/**
 * Excepción por Incompatibilidad e estados en un curso.
 */
public final class CursoModuloEstadoIncompatibleException extends InconsistencyDataException{

	public CursoModuloEstadoIncompatibleException(String messageKey, Object dto, String...args ) {
		super(messageKey, dto, args);
	}

}
