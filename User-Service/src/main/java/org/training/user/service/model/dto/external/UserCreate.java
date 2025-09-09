package org.training.user.service.model.dto.external;

public class UserCreate {

    private Long userId;
    private String firstName;
    private String lastName;
    private String identificationNumber;

    // No-args constructor
    public UserCreate() {
    }

    // All-args constructor
    public UserCreate(Long userId, String firstName, String lastName, String identificationNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identificationNumber = identificationNumber;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }
}
