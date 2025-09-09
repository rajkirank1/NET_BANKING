package org.training.user.service.model.dto;

import org.training.user.service.model.Status;

public class UserUpdateStatus {

    private Status status;

    public UserUpdateStatus() {
    }

    public UserUpdateStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
