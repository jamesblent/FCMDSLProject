package abc.docker.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class ForbiddenRequestException extends RuntimeException {

	private static final long serialVersionUID = -470180507998010368L;



	public ForbiddenRequestException(final String message) {
		super(message);
	}


}
