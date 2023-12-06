package modu.menu.core.exception;


import lombok.Getter;
import modu.menu.core.response.ApiResponse;
import org.springframework.http.HttpStatus;


@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ApiResponse<?> body() {
        return new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}