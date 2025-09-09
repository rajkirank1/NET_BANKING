package org.training.transactions.exception;

public class InsufficientBalance extends GlobalException {

    private static final long serialVersionUID = 1L;

    public InsufficientBalance(String message) {
        super(message, GlobalErrorCode.BAD_REQUEST);
    }
}
