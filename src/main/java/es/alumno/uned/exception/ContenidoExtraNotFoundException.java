package es.alumno.uned.exception;
/**
 * Excepción por no existir el Contenido Extra de un curso.
 */
public class ContenidoExtraNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ContenidoExtraNotFoundException(String mensaje) {
		super(mensaje);
	}
	
}
