package modu.menu.core.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import modu.menu.core.exception.*;
import modu.menu.core.response.ApiFailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("400: " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiFailResponse(
                        HttpStatus.BAD_REQUEST,
                        e.getFieldErrors().get(0).getField(),
                        e.getFieldErrors().get(0).getDefaultMessage())
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("400: " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiFailResponse(
                        HttpStatus.BAD_REQUEST,
                        e.getConstraintViolations().stream()
                                .map(ConstraintViolation::getInvalidValue)
                                .map(o -> String.valueOf(o))
                                .collect(Collectors.joining()),
                        e.getConstraintViolations().stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining()))
                );
    }

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e) {
        log.warn("400: " + e.getMessage());
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unauthorized(Exception401 e) {
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
        log.error("500: " + e.getMessage(), e);
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e) {
        log.error("500: " + e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiFailResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage()
                ));
    }
}
