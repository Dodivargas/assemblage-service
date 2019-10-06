package com.github.dodivargas.assemblageservice.exception;

import org.springframework.http.HttpStatus;

public class ClosedRulingException extends HttpException {

    private static final String MESSAGE = "This ruling is closed for votes";

    public ClosedRulingException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}