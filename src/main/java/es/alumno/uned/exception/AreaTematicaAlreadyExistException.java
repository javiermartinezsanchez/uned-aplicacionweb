package es.alumno.uned.exception;

public class AreaTematicaAlreadyExistException extends RuntimeException{

	    private static final long serialVersionUID = 5861310537366287163L;

	    public AreaTematicaAlreadyExistException() {
	        super();
	    }

	    public AreaTematicaAlreadyExistException(final String message, final Throwable cause) {
	        super(message, cause);
	    }

	    public AreaTematicaAlreadyExistException(final String message) {
	        super(message);
	    }

	    public AreaTematicaAlreadyExistException(final Throwable cause) {
	        super(cause);
	    }

}
