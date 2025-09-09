package org.training.user.service.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.training.user.service.model.Status;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String emailId;

    private String contactNo;

    private String authId;

    private String identificationNumber;

    @CreationTimestamp
    private LocalDate creationOn;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "userProfileId")
    private UserProfile userProfile;

    // No-args constructor
    public User() {
    }

    // All-args constructor
    public User(Long userId, String emailId, String contactNo, String authId,
                String identificationNumber, LocalDate creationOn, Status status,
                UserProfile userProfile) {
        this.userId = userId;
        this.emailId = emailId;
        this.contactNo = contactNo;
        this.authId = authId;
        this.identificationNumber = identificationNumber;
        this.creationOn = creationOn;
        this.status = status;
        this.userProfile = userProfile;
    }

    // Getters and Setters
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public LocalDate getCreationOn() {
        return creationOn;
    }

    public void setCreationOn(LocalDate creationOn) {
        this.creationOn = creationOn;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
