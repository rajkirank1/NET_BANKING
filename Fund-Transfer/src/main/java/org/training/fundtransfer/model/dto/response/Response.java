package org.training.fundtransfer.model.dto.response;

public class Response {

    private String responseCode;

    private String message;

    // No-argument constructor
    public Response() {
    }

    // All-argument constructor
    public Response(String responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String responseCode;
        private String message;

        public Builder responseCode(String responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Response build() {
            return new Response(responseCode, message);
        }
    }
}
