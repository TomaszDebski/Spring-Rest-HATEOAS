package pl.springTests.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

	public ConflictException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConflictException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
