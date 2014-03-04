package exceptions;

/**
 * Exception occurring when player wants to draw already drawn line, or a line
 * outside the game board.
 * 
 * @author random.org
 * 
 */
public class InvalidLineIndexingException extends Exception {

	private static final long serialVersionUID = 5618395266958241693L;

	public InvalidLineIndexingException() {
		super();
	}

	public InvalidLineIndexingException(String message) {
		super(message);
	}

	public InvalidLineIndexingException(Throwable cause) {
		super(cause);
	}

	public InvalidLineIndexingException(String message, Throwable cause) {
		super(message, cause);
	}

}
