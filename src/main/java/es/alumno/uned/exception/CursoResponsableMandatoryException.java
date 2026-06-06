package es.alumno.uned.exception;

/**
 * Excepción por faltar el "Responsable"  en un curso.
 */
public final class CursoResponsableMandatoryException extends SinDTOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3911743781622446477L;

	public CursoResponsableMandatoryException(String messageKey) {
		super(messageKey);
	}
}
