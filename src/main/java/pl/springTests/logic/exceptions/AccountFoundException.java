package pl.springTests.logic.exceptions;

/**
 * @author Tomasz Dębski
 *
 */

public class AccountFoundException extends RuntimeException{

	public AccountFoundException() {
		super();
	}

	public AccountFoundException(String message) {
		super(message);
	}

	public AccountFoundException(Throwable cause) {
		super(cause);
	}
}
