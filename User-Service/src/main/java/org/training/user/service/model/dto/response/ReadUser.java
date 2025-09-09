package org.training.user.service.model.dto.response;

import org.training.user.service.model.Status;

public class ReadUser {

    private String firstName;
    private String lastName;
    private String emailId;
    private String contactNumber;
    private Status status;

    // No-args constructor
    public ReadUser() {
    }

    // All-args constructor
    public ReadUser(String firstName, String lastName, String emailId, String contactNumber, Status status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.contactNumber = contactNumber;
        this.status = status;
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
