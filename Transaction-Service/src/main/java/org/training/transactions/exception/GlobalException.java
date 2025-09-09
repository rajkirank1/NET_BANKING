package org.training.transactions.exception;

public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public GlobalException(String message) {
        super(message);
        this.errorCode = null;
    }

    public GlobalException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
