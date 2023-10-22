package ru.solovetskyJungs.museum.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionDTO> handle(Exception e) {
        return ResponseEntity.badRequest().body(
                new ApiExceptionDTO(e, e.getMessage()
                )
        );
    }
}
