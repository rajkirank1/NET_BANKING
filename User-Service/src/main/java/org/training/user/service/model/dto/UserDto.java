package org.training.user.service.model.dto;

import org.training.user.service.model.Status;

public class UserDto {

    private Long userId;
    private String emailId;
    private String password;
    private String identificationNumber;
    private String authId;
    private Status status;
    private UserProfileDto userProfileDto;

    public UserDto() {
    }

    public UserDto(Long userId, String emailId, String password, String identificationNumber,
                   String authId, Status status, UserProfileDto userProfileDto) {
        this.userId = userId;
        this.emailId = emailId;
        this.password = password;
        this.identificationNumber = identificationNumber;
        this.authId = authId;
        this.status = status;
        this.userProfileDto = userProfileDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserProfileDto getUserProfileDto() {
        return userProfileDto;
    }

    public void setUserProfileDto(UserProfileDto userProfileDto) {
        this.userProfileDto = userProfileDto;
    }
}
