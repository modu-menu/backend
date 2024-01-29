package modu.menu.core.exception;

import lombok.Getter;
import modu.menu.core.response.ApiCommonResponse;
import modu.menu.core.response.ErrorMessage;
import org.springframework.http.HttpStatus;


@Getter
public class Exception403 extends RuntimeException {
    public Exception403(ErrorMessage message) {
        super(message.getValue());
    }

    public ApiCommonResponse<?> body() {
        return new ApiCommonResponse<>(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}