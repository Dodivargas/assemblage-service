package com.github.dodivargas.assemblageservice.exception;

import org.springframework.http.HttpStatus;

public class RuleNotOpenForVoteException extends HttpException {

    private static final String MESSAGE = "This rule is not open for vote.";

    public RuleNotOpenForVoteException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
