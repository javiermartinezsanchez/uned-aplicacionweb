package es.alumno.uned.exception;

/**
 * Excepción generada por no coincidir el responsable con el curso solicitado.
 */
public final class ResponsableNotMachtException extends SinDTOException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3688324349326618164L;

	public ResponsableNotMachtException(String messageKey) {
		super(messageKey);
	}

}
