package com.github.dodivargas.assemblageservice.exception;

import org.springframework.http.HttpStatus;

public class RulingNotOpenForVoteException extends HttpException {

    private static final String MESSAGE = "This rule is not open for vote.";

    public RulingNotOpenForVoteException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
