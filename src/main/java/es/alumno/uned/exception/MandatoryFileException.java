package es.alumno.uned.exception;

/**
 * Excepción que se genera cuando es obligatoria la existencia de un fichero y no se encuentra.
 */
public final class MandatoryFileException extends MandatoryDataException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MandatoryFileException(String messageKey, Object dto, String... params ) {
		super(messageKey, dto, params);
	}

}
