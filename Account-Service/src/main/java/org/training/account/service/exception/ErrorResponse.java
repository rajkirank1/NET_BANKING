package org.training.account.service.exception;


public class ErrorResponse {
    private String errorCode;
    private String message;

    // Add public setters!
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    // Builder pattern
    public static class Builder {
        private String errorCode;
        private String message;

        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        public ErrorResponse build() {
            ErrorResponse response = new ErrorResponse();
            // Use the setters (they must be defined in ErrorResponse)
            response.setErrorCode(this.errorCode);
            response.setMessage(this.message);
            return response;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
