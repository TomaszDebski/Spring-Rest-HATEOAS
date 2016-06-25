package pl.springTests.logic.exceptions;

/**
 * @author Tomasz Dębski
 *
 */

public class AccountNotFoundException extends RuntimeException {

	public AccountNotFoundException() {
		super();
	}

	public AccountNotFoundException(String message) {
		super(message);
	}

	public AccountNotFoundException(Throwable cause) {
		super(cause);
	}
	
	

}
