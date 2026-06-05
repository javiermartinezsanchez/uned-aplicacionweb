package es.alumno.uned.exception;

import java.util.Objects;

public final class MandatoryFileException extends MandatoryDataException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MandatoryFileException(String messageKey, Object dto, Objects...args ) {
		super(messageKey, dto, args);
	}

}
