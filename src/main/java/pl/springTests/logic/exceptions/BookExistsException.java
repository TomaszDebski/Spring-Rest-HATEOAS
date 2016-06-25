package pl.springTests.logic.exceptions;

/**
 * @author Tomasz Dębski
 *
 */

public class BookExistsException extends RuntimeException {

	public BookExistsException() {
		super();
	}

	public BookExistsException(String message) {
		super(message);
	}

	public BookExistsException(Throwable cause) {
		super(cause);
	}

	
}
