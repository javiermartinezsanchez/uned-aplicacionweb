package es.alumno.uned.exception;

public class MandatoryModuloException extends MandatoryDataException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MandatoryModuloException(String messageKey, Object dto, String... args) {
		super(messageKey, dto, args);
		
	}

	
}
