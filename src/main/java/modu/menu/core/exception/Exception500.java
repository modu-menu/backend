package modu.menu.core.exception;

import lombok.Getter;
import modu.menu.core.response.ApiFailResponse;
import modu.menu.core.response.ErrorMessage;
import org.springframework.http.HttpStatus;

@Getter
public class Exception500 extends RuntimeException {
    public Exception500(ErrorMessage message) {
        super(message.getValue());
    }

    public ApiFailResponse body(){
        return new ApiFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}