package org.training.transactions.exception;

public class ErrorResponse {
    private String errorCode;
    private String message;

    private ErrorResponse(Builder builder) {
        this.errorCode = builder.errorCode;
        this.message = builder.message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public static Builder builder() {
        return new Builder();
    }

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
            return new ErrorResponse(this);
        }
    }
}
