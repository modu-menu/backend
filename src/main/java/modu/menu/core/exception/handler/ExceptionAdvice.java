package modu.menu.core.exception.handler;

import lombok.extern.slf4j.Slf4j;
import modu.menu.core.exception.*;
import modu.menu.core.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e) {
        log.warn("400: " + e.getMessage());
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e) {
        log.warn("401: " + e.getMessage());
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e) {
        log.warn("403: " + e.getMessage());
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e) {
        log.warn("404: " + e.getMessage());
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e) {
        log.error("500: " + e);
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e) {
        log.error("Unknown: " + e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        e.getMessage()
                ));
    }
}
