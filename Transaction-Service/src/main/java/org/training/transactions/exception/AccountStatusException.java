package org.training.transactions.exception;

public class AccountStatusException extends GlobalException {

    private static final long serialVersionUID = 1L;

    public AccountStatusException(String message) {
        super(message, GlobalErrorCode.BAD_REQUEST);
    }
}
