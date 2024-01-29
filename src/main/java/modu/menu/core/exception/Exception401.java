package modu.menu.core.exception;


import lombok.Getter;
import modu.menu.core.response.ApiCommonResponse;
import modu.menu.core.response.ErrorMessage;
import org.springframework.http.HttpStatus;


@Getter
public class Exception401 extends RuntimeException {
    public Exception401(ErrorMessage message) {
        super(message.getValue());
    }

    public ApiCommonResponse<?> body() {
        return new ApiCommonResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}