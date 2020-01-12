package abc.docker.services.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abc.docker.services.model.response.ExceptionResponse;
import abc.docker.services.model.response.ResponseMeta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {


	@ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ExceptionResponse handleException(final Exception exception,
                                      final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
        ResponseMeta meta=new ResponseMeta();
        meta.setCode("0000");
        meta.setMessage("Internal server error");
        if(exception.getMessage()!=null) {
            if (exception.getMessage().contains("@")) {
                meta.setCode(exception.getMessage().split("@")[0]);
                meta.setMessage(exception.getMessage().split("@")[1]);
            }
        }
        error.setMeta(meta);

		return error;
	}

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ExceptionResponse handleException(final BadRequestException exception,
                                      final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        ResponseMeta meta=new ResponseMeta();
        meta.setCode("0000");
        meta.setMessage("Internal server error");
        if(exception.getMessage()!=null) {
            if (exception.getMessage().contains("@")) {
                String[] exceptionArray=null;
                exceptionArray=exception.getMessage().split("@");

                meta.setCode(exceptionArray[0]);
                meta.setMessage(exceptionArray[1]);


            }
        }
        error.setMeta(meta);

        return error;
    }

    @ExceptionHandler(ForbiddenRequestException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public @ResponseBody
    ExceptionResponse handleException(final ForbiddenRequestException exception,
                                      final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        ResponseMeta meta=new ResponseMeta();
        meta.setCode("403 Forbidden");
        meta.setMessage(exception.getMessage());
        error.setMeta(meta);

        return error;
    }


}
