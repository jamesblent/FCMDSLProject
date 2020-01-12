package abc.docker.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;



	public InternalServerException(final String message) {
		super(message);
	}


}
