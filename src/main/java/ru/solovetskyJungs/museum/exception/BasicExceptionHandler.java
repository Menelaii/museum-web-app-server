package ru.solovetskyJungs.museum.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BasicExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(BasicExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionDTO> handle(Exception e) {
        logger.error(e.toString() + " " + e.getMessage());

        return ResponseEntity.badRequest().body(
                new ApiExceptionDTO(e, e.getMessage()
                )
        );
    }
}
