package es.alumno.uned.exception;

public final class MandatoryFileException extends MandatoryDataException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MandatoryFileException(String messageKey, Object dto, String...args ) {
		super(messageKey, dto, args);
	}

}
