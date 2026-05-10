package es.alumno.uned.exception;
/**
 * Excepción de la necesidad de la existencia de un Módulo
 */
public final class MandatoryModuloException extends MandatoryDataException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MandatoryModuloException(String messageKey, Object dto, String... args) {
		super(messageKey, dto, args);
		
	}

	
}
