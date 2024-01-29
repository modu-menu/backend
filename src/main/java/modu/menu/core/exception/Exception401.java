package modu.menu.core.exception;


import lombok.Getter;
import modu.menu.core.response.ApiFailResponse;
import modu.menu.core.response.ApiSuccessResponse;
import modu.menu.core.response.ErrorMessage;
import org.springframework.http.HttpStatus;


@Getter
public class Exception401 extends RuntimeException {
    public Exception401(ErrorMessage message) {
        super(message.getValue());
    }

    public ApiFailResponse body() {
        return new ApiFailResponse(HttpStatus.UNAUTHORIZED, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}