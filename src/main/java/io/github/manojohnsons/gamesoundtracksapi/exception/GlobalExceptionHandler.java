package io.github.manojohnsons.gamesoundtracksapi.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.media.Schema;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException e) {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN,
                "Acesso negado. Você não tem permissão para executar esta ação.");

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorData>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors();
        var errorsList = errors.stream()
                .map(error -> new ValidationErrorData(error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(errorsList);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(ResourceAlreadyExistsException e) {
        ApiError error = new ApiError(HttpStatus.CONFLICT, e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException e) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED,
                "Credenciais inválidas. Verifique o nome de usuário e a senha.");

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedException(Exception e) {
        LOGGER.error("Ocorreu uma exceção inesperada: ", e);
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado do servidor. Tente novamente mais tarde.");

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ApiError(
            @Schema(description = "HTTP status of the error.", example = "NOT_FOUND") HttpStatus status,
            @Schema(description = "A human-readable message describing the error", example = "Game not found with the specified ID: 99") String message) {
    }

    public record ValidationErrorData(
            @Schema(description = "Field with the invalid value.") String field,
            @Schema(description = "Validation error message") String message) {
    }
}
