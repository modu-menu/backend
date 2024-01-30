package modu.menu.core.exception;

import lombok.Getter;
import modu.menu.core.response.ApiFailResponse;
import modu.menu.core.response.ApiSuccessResponse;
import modu.menu.core.response.ErrorMessage;
import org.springframework.http.HttpStatus;


@Getter
public class Exception404 extends RuntimeException {
    public Exception404(ErrorMessage message) {
        super(message.getValue());
    }

    public ApiFailResponse body() {
        return new ApiFailResponse(HttpStatus.NOT_FOUND, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}