package pl.springTests.logic.exceptions;

/**
 * @author Tomasz Dębski
 *
 */

public class UserExistsException extends RuntimeException{

	public UserExistsException() {
		super();
	}

	public UserExistsException(String message) {
		super(message);
	}

	public UserExistsException(Throwable cause) {
		super(cause);
	}
	
	

}
