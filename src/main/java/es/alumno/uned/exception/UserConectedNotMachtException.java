package es.alumno.uned.exception;
/**
 * 
 * Excepción que generamos cuando el Usuario conectado intenta realizar alguna opción sobre los datos
 * de otro usuario.
 * 
 * 
 */
public final class UserConectedNotMachtException extends InconsistencyDataException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserConectedNotMachtException(String messageKey, Object dto, String... args) {
        super(messageKey, dto, args);
	}
}
