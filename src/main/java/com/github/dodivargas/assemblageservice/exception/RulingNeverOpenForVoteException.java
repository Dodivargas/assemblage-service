package com.github.dodivargas.assemblageservice.exception;

import org.springframework.http.HttpStatus;

public class RulingNeverOpenForVoteException extends HttpException {

    private static final String MESSAGE = "This rule is never open for vote.";

    public RulingNeverOpenForVoteException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
