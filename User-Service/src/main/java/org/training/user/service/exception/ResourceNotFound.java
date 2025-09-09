package org.training.user.service.exception;

public class ResourceNotFound extends GlobalException{
	  private static final long serialVersionUID = 1L;
    public ResourceNotFound() {
        super("Resource not found on the server", GlobalError.NOT_FOUND);
    }

    public ResourceNotFound(String message) {
        super(message, GlobalError.NOT_FOUND);
    }
}
