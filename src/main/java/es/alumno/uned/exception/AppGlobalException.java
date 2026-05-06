package es.alumno.uned.exception;
/** 
 * Clase abstracta genérica de agrupación de las Excepciones que generamos en la aplicación.
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

public abstract class AppGlobalException extends RuntimeException {

	private static final long serialVersionUID = -3721888412181708452L;
	/**
	 * Objeto del modelo que ha generado el error, normalmente un DTO con los campos y sus valores.
	 */
	private final Object dto;
    private final Object[] args;

    protected AppGlobalException(String messageKey, Object dto, Object... args) {
        super(messageKey);
        this.dto = dto;
        this.args = args;
    }

    public Object getDto() {
        return dto;
    }

    public Object[] getArgs() {
        return args;
    }

}
