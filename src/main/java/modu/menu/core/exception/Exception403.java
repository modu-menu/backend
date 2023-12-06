package modu.menu.core.exception;

import lombok.Getter;
import modu.menu.core.response.ApiResponse;
import org.springframework.http.HttpStatus;


@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ApiResponse<?> body() {
        return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}