package modu.menu.core.exception;

import lombok.Getter;
import modu.menu.core.response.ApiFailResponse;
import modu.menu.core.response.ApiSuccessResponse;
import modu.menu.core.response.ErrorData;
import org.springframework.http.HttpStatus;


@Getter
public class Exception400 extends RuntimeException {

    private String key;
    private String value;

    public Exception400(String key, String value) {
        super(value);
        this.key = key;
        this.value = value;
    }

    public ApiFailResponse body() {
        return new ApiFailResponse(HttpStatus.BAD_REQUEST, key, value);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}