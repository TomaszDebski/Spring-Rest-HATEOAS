package pl.springTests.logic.exceptions;

/**
 * @author Tomasz Dębski
 *
 */

public class AuthorNotFoundException extends RuntimeException {

	public AuthorNotFoundException() {
		super();
	}

	public AuthorNotFoundException(String message) {
		super(message);
	}

	public AuthorNotFoundException(Throwable cause) {
		super(cause);
	}
	
	

}
