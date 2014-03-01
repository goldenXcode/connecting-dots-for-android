package doublelinkedlist.exceptions;

public class InvalidDataParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -132378873796729352L;

	public InvalidDataParameterException() {
		super();
	}

	public InvalidDataParameterException(String message) {
		super(message);
	}

	public InvalidDataParameterException(Throwable cause) {
		super(cause);
	}

	public InvalidDataParameterException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
