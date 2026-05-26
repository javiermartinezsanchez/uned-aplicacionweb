package es.alumno.uned.exception;
/**
 * Excepción de cuando un Estudiante está ya subscrito en un curso.
 */
public final class  EstudianteCursoAlreadySubscribeException extends SinDTOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5159533174395521517L;
	
    public EstudianteCursoAlreadySubscribeException(String message) {
    	super(message);
    }
}