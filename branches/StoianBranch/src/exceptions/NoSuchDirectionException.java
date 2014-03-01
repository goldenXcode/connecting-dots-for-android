package exceptions;

/**
 * Exception occurring when a user enters an invalid input for line direction.
 * 
 * @author random.org
 *
 */
public class NoSuchDirectionException extends Exception {

	private static final long serialVersionUID = 3037459690440928387L;

	public NoSuchDirectionException() {
		super();
	}

	public NoSuchDirectionException(String message) {
		super(message);
	}

	public NoSuchDirectionException(Throwable cause) {
		super(cause);
	}

	public NoSuchDirectionException(String message, Throwable cause) {
		super(message, cause);
	}

}
