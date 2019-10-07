package com.github.dodivargas.assemblageservice.client.response;

public class CpfValidationResponse {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public static final class CpfValidationResponseBuilder {
        private String status;

        private CpfValidationResponseBuilder() {
        }

        public static CpfValidationResponseBuilder of() {
            return new CpfValidationResponseBuilder();
        }

        public CpfValidationResponseBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public CpfValidationResponse build() {
            CpfValidationResponse cpfValidationResponse = new CpfValidationResponse();
            cpfValidationResponse.setStatus(status);
            return cpfValidationResponse;
        }
    }
}
