package modu.menu.core.exception;

import lombok.Getter;
import modu.menu.core.response.ApiFailResponse;
import org.springframework.http.HttpStatus;


@Getter
public class Exception400 extends RuntimeException {

    private String cause;
    private String message;

    public Exception400(String cause, String message) {
        super(message);
        this.cause = cause;
        this.message = message;
    }

    public ApiFailResponse body() {
        return new ApiFailResponse(HttpStatus.BAD_REQUEST, cause, message);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}