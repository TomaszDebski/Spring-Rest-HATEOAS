package pl.springTests.logic.exceptions;

/**
 * @author Tomasz Dębski
 *
 */

public class BookNotFoundException  extends RuntimeException{

	public BookNotFoundException() {
		super();
	}

	public BookNotFoundException(String message) {
		super(message);
	}

	public BookNotFoundException(Throwable cause) {
		super(cause);
	}
	
	

}
