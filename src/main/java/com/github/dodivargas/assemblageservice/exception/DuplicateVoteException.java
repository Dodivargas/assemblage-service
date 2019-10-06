package com.github.dodivargas.assemblageservice.exception;


import org.springframework.http.HttpStatus;

public class DuplicateVoteException extends HttpException {

    private static final String MESSAGE = "Already exists a vote for this TaxId.";

    public DuplicateVoteException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}