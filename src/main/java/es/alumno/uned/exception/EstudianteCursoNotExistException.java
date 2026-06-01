package es.alumno.uned.exception;
/**
 * Excepción de cuando un Estudiante quiere darse de baja o acceder a un curso y no existe
 */
public final class EstudianteCursoNotExistException extends SinDTOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3085174431770453236L;

	public EstudianteCursoNotExistException(String messageKey, String...string) {
		super(messageKey, string);
	}

}
