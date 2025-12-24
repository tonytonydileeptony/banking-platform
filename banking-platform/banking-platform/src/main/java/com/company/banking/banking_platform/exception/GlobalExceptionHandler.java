package com.company.banking.banking_platform.exception;



import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(
                        404,
                        "NOT_FOUND",
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusiness(
            BusinessException ex,
            HttpServletRequest request) {

        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse(
                        400,
                        "BAD_REQUEST",
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiErrorResponse(
                        403,
                        "FORBIDDEN",
                        "You are not allowed to access this resource",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(new ApiError(
                        request.getRequestURI(),
                        message,
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    // 🔐 Bad credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(
                        request.getRequestURI(),
                        "Invalid username or password",
                        HttpStatus.UNAUTHORIZED.value()
                ));
    }

    // ⛔ Forbidden




    // 🔥 Fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        request.getRequestURI(),
                        "Internal server error",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }
}
