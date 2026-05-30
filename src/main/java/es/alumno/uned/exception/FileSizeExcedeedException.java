package es.alumno.uned.exception;

public class FileSizeExcedeedException extends RuntimeException {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7461374155079867793L;
	private final Object dto;
	private final Object[] args = {};
	public FileSizeExcedeedException(String message, Object dto) {
		super(message);
		this.dto = dto;
		 
	}

	public Object getDto() {
		return this.dto;
	}
	public Object[] getArgs() {
        return args;
    }
}
