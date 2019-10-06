package com.github.dodivargas.assemblageservice.config;

import com.github.dodivargas.assemblageservice.exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public ResponseEntity<?> handleException(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException e = (HttpException) throwable;
            this.log.error(e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
        this.log.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleJsonProcessingException(HttpMessageNotReadableException e) {
        this.log.error(e.getMessage(), e);
        return new ResponseEntity<>("Invalid input json", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerServerInputException(MethodArgumentNotValidException e) {
        this.log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(Objects.requireNonNull(e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)));
    }
}
