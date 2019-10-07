package com.github.dodivargas.assemblageservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidTaxIdException extends HttpException {

    private static final String MESSAGE = "This taxId is invalid.";

    public InvalidTaxIdException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
