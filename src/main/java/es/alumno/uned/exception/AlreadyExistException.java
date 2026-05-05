package es.alumno.uned.exception;

/** 
 * Clase genérica para Excepciones de "Ya existe el dato"
 * 
 * <p>Se generarán en las post-validaciones de los formularios.
 * 
 * <ul>Atributos
 * <li><b>dto:</b> Clase DTO con los datos del formulario</li>
 * <li><b>args:</b> Array de valores a mostrar en el mensajes de la excepción</li>
 * </ul>
 * <p>Estas excepciones se gestionan con un {@code @ControllerAdvice} implementado.
 */
public abstract class AlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -4028748522198142783L;
	private final Object dto;
    private final Object[] args;

    protected AlreadyExistException(String messageKey, Object dto, Object... args) {
        super(messageKey);   // aquí guardamos la CLAVE i18n, no el texto
        this.dto = dto;      // valores a mostrar en el mensaje como parámetros {0}, {1}, ...{n}
        this.args = args;
    }

    public Object getDto() {
        return dto;
    }

    public Object[] getArgs() {
        return args;
    }
}

