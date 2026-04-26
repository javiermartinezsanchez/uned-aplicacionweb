package es.alumno.uned.exception;

public class UserPasswordNotMatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4227999226031605506L;

	public UserPasswordNotMatchException() {
		super();
	}

	public UserPasswordNotMatchException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UserPasswordNotMatchException(final String message) {
		super(message);
	}
	
	
	public UserPasswordNotMatchException(final Throwable cause) {
		super(cause);
	}

}
