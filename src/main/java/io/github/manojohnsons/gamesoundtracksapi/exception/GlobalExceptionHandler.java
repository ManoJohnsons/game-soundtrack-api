package io.github.manojohnsons.gamesoundtracksapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException e) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidToken(InvalidTokenException e) {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN, e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedException(Exception e) {
        LOGGER.error("Ocorreu uma exceção inesperada: ", e);
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado do servidor. Tente novamente mais tarde.");

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ApiError(HttpStatus status, String message) {}
}
