package es.alumno.uned.exception;

/**
 * Excepción por "inconsistenecia", es decir no coinciden la contraseña nueva con su confirmación.
 * <p>En los cambios de password.
 */
public final class UserPasswordNotMatchException extends InconsistencyDataException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4227999226031605506L;

	public UserPasswordNotMatchException(String messageKey, Object dto, String... args) {
        super(messageKey, dto, args);
	}
}
