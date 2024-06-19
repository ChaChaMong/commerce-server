package com.unknown.commerce.global.exception;

import com.unknown.commerce.global.response.ErrorResponse;
import com.unknown.commerce.global.response.HttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Custom Exception Handler
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handlerBusinessException(BusinessException e, HttpServletRequest request) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.of(
                        e.getMessage(),
                        request
                ));
    }

    /**
     * 설정하지 않은 Exception 처리 Handler
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerAllException(Exception e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpResponse.Fail.INTERNAL_SERVER_ERROR.getStatus())
                .body(ErrorResponse.of(
                        HttpResponse.Fail.INTERNAL_SERVER_ERROR.getMessage(),
                        request,
                        e
                ));
    }
}
