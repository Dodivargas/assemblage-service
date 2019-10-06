package com.github.dodivargas.assemblageservice.exception;

import org.springframework.http.HttpStatus;

public class RulingNotFoundException extends HttpException {

    private static final String MESSAGE = "This rule not exists.";

    public RulingNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
