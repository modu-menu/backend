package modu.menu.core.exception;

import lombok.Getter;
import modu.menu.core.response.ApiCommonResponse;
import modu.menu.core.response.ErrorMessage;
import org.springframework.http.HttpStatus;


@Getter
public class Exception404 extends RuntimeException {
    public Exception404(ErrorMessage message) {
        super(message.getValue());
    }

    public ApiCommonResponse<?> body() {
        return new ApiCommonResponse<>(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}