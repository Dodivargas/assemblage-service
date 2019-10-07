package com.github.dodivargas.assemblageservice.stubs;

import com.github.dodivargas.assemblageservice.client.response.CpfValidationResponse;

public class CpfValidationStub {

    public static CpfValidationResponse buildAbleToVote() {
        CpfValidationResponse cpfValidationResponse = new CpfValidationResponse();
        cpfValidationResponse.setStatus("ABLE_TO_VOTE");
        return cpfValidationResponse;
    }


    public static CpfValidationResponse buildUnAbleToVote() {
        CpfValidationResponse cpfValidationResponse = new CpfValidationResponse();
        cpfValidationResponse.setStatus("UNABLE_TO_VOTE");
        return cpfValidationResponse;
    }
}
