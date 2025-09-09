package org.training.transactions.exception;

public class ResourceNotFound extends GlobalException {

    private static final long serialVersionUID = 1L; // Add this line

    public ResourceNotFound(String errorCode, String message) {
        super(errorCode, message);
    }
}
