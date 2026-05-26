package es.alumno.uned.exception;


/** 
 * Clase abstracta genérica para Excepciones que no vienen de un formulario
 * 
 * <p>Se generarán en los service que no reciban el DTO de la vista.
 * 
 * <p><b>Atributos</b>
 * <ul>
 * <li><b>messageKey:</b> Clave i18n para mostrar.</li>
 * </ul>
 * <p>Estas excepciones se gestionan con un {@code @ControllerAdvice} implementado.
 */
public abstract class SinDTOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5223919151333529225L;

	protected SinDTOException(String messageKey) {
		super(messageKey);
	}
}
