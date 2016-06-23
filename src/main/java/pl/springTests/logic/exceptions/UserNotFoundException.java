package pl.springTests.logic.exceptions;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
	}

	public UserNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	

}
