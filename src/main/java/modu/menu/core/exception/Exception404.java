package modu.menu.core.exception;

import lombok.Getter;
import modu.menu.core.response.ApiResponse;
import org.springframework.http.HttpStatus;


@Getter
public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }

    public ApiResponse<?> body() {
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}