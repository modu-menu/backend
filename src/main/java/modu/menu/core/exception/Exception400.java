package modu.menu.core.exception;

import lombok.Getter;
import modu.menu.core.response.ApiCommonResponse;
import modu.menu.core.response.ErrorData;
import modu.menu.core.response.ErrorMessage;
import org.springframework.http.HttpStatus;


@Getter
public class Exception400 extends RuntimeException {

    private String key;
    private String value;

    public Exception400(String key, ErrorMessage value) {
        super(value.getValue());
        this.key = key;
        this.value = value.getValue();
    }

    public ApiCommonResponse<?> body() {
        return new ApiCommonResponse<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), new ErrorData(key, value));
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}