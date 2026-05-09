package es.alumno.uned.exception;

public final class ModuloNotFoundException extends NotDataFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5724092550805323431L;

	public ModuloNotFoundException(String messageKey, Object dto, String... args) {
		super(messageKey, dto, args);
	}

}
