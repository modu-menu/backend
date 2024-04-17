package modu.menu.core.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ApiFailResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("Exception: 400, " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiFailResponse(
                        HttpStatus.BAD_REQUEST,
                        e.getFieldErrors().get(0).getField(),
                        e.getFieldErrors().get(0).getDefaultMessage())
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiFailResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("Exception: 400, " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiFailResponse(
                        HttpStatus.BAD_REQUEST,
                        e.getConstraintViolations().stream()
                                .map(ConstraintViolation::getInvalidValue)
                                .map(String::valueOf)
                                .collect(Collectors.joining()),
                        e.getConstraintViolations().stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining()))
                );
    }

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<ApiFailResponse> badRequest(Exception400 e) {
        log.warn("Exception: 400, " + e.getMessage());
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<ApiFailResponse> unauthorized(Exception401 e) {
        log.warn("Exception: 401, " + e.getMessage());
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<ApiFailResponse> forbidden(Exception403 e) {
        log.warn("Exception: 403, " + e.getMessage());
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<ApiFailResponse> notFound(Exception404 e) {
        log.warn("Exception: 404, " + e.getMessage());
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<ApiFailResponse> serverError(Exception500 e, HttpServletRequest request) {
        log.error("Exception: 500, " + request.getMethod() + ": " + request.getRequestURI(), e);
        return ResponseEntity
                .status(e.status())
                .body(e.body());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiFailResponse> unknownServerError(Exception e, HttpServletRequest request) {
        log.error("Exception: 500, " + request.getMethod() + ": " + request.getRequestURI(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiFailResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage()
                ));
    }
}
