package pl.springTests.logic.exceptions;

/**
 * @author Tomasz Dębski
 *
 */

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}

	

}
