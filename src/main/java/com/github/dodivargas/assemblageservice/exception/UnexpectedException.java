package com.github.dodivargas.assemblageservice.exception;

import org.springframework.http.HttpStatus;

public class UnexpectedException extends HttpException {

    private static final String MESSAGE = "This user is unable for vote.";

    public UnexpectedException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
