package com.github.dodivargas.assemblageservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotAbleToVoteException extends HttpException {

    private static final String MESSAGE = "This user is unable for vote.";

    public UserNotAbleToVoteException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
