package org.training.user.service.exception;

public class EmptyFields extends GlobalException {

    private static final long serialVersionUID = 1L;

    public EmptyFields(String message, String errorCode) {
        super(message, errorCode);
    }
}
