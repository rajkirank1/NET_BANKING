package org.training.account.service.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.training.account.service.model.AccountStatus;
import org.training.account.service.model.AccountType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @CreationTimestamp
    private LocalDate openingDate;

    private BigDecimal availableBalance;

    private Long userId;

    // No-argument constructor
    public Account() {
    }

    // All-argument constructor
    public Account(Long accountId, String accountNumber, AccountType accountType, AccountStatus accountStatus,
                   LocalDate openingDate, BigDecimal availableBalance, Long userId) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.openingDate = openingDate;
        this.availableBalance = availableBalance;
        this.userId = userId;
    }

    // Getters and setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // toString method
    @Override
    public String toString() {
        return "Account{" +
               "accountId=" + accountId +
               ", accountNumber='" + accountNumber + '\'' +
               ", accountType=" + accountType +
               ", accountStatus=" + accountStatus +
               ", openingDate=" + openingDate +
               ", availableBalance=" + availableBalance +
               ", userId=" + userId +
               '}';
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return accountId != null && accountId.equals(account.accountId);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return accountId != null ? accountId.hashCode() : 0;
    }

    // Optional: You can implement a Builder class for manual building, if needed
}
