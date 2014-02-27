package doublelinkedlist.exceptions;

public class LinkedListIndexException extends Exception {

	private static final long serialVersionUID = -697552398663701662L;

	public LinkedListIndexException() {
		super();
	}

	public LinkedListIndexException(String message) {
		super(message);
	}

	public LinkedListIndexException(Throwable cause) {
		super(cause);
	}

	public LinkedListIndexException(String message, Throwable cause) {
		super(message, cause);
	}

}
