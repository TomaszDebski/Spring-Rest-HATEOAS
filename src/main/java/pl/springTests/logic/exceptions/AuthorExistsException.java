package pl.springTests.logic.exceptions;

/**
 * @author Tomasz Dębski
 *
 */

public class AuthorExistsException extends RuntimeException {

	public AuthorExistsException() {
		super();
	}

	public AuthorExistsException(String message) {
		super(message);
	}

	public AuthorExistsException(Throwable cause) {
		super(cause);
	}
	
	

}
