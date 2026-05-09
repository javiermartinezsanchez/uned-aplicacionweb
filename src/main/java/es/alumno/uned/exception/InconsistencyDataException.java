package es.alumno.uned.exception;

/** 
 * Clase abstracta genérica para Excepciones de "Inconsistencia de datos"
 * 
 * <p>Se generarán en las post-validaciones de los formularios.
 * 
 * <p><b>Atributos</b>
 * <ul>
 * <li><b>dto:</b> Clase DTO con los datos del formulario</li>
 * <li><b>args:</b> Array de valores a mostrar en el mensajes de la excepción</li>
 * </ul>
 * <p>Estas excepciones se gestionan con un {@code @ControllerAdvice} implementado.
 */
public abstract class InconsistencyDataException extends AppGlobalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -935190537681722695L;

	protected InconsistencyDataException(String messageKey, Object dto, Object[] args) {
		super(messageKey, dto, args);
		
	}

}
