package org.training.account.service.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${spring.application.bad_request:Bad Request}")
    private String badRequest;

    @Value("${spring.application.conflict:Conflict}")
    private String conflict;

    @Value("${spring.application.not_found:Not Found}")
    private String notFound;

    /**
     * Handles validation errors (MethodArgumentNotValidException)
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorResponse error = ErrorResponse.builder()
                .errorCode(badRequest)
                .message(ex.getBindingResult().getAllErrors().stream()
                        .map(err -> err.getDefaultMessage())
                        .findFirst().orElse(ex.getLocalizedMessage()))
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles your custom GlobalException
     */
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> handleGlobalException(GlobalException globalException) {
        ErrorResponse error = ErrorResponse.builder()
                .errorCode(globalException.getErrorCode())
                .message(globalException.getErrorMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // You can add handlers for other exceptions (e.g., NotFoundException, ConflictException) similarly.
}
