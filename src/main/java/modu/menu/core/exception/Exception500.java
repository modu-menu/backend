package modu.menu.core.exception;

import lombok.Getter;
import modu.menu.core.response.ApiCommonResponse;
import modu.menu.core.response.ErrorMessage;
import org.springframework.http.HttpStatus;

@Getter
public class Exception500 extends RuntimeException {
    public Exception500(ErrorMessage message) {
        super(message.getValue());
    }

    public ApiCommonResponse<?> body() {
        return new ApiCommonResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
