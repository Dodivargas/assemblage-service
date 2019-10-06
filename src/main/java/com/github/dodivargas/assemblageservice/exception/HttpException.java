package com.github.dodivargas.assemblageservice.exception;

import org.springframework.http.HttpStatus;

public abstract class HttpException extends RuntimeException {

    public HttpException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}
