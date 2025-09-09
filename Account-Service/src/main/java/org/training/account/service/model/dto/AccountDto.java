package org.training.account.service.model.dto;

import java.math.BigDecimal;

public class AccountDto {

    private Long accountId;
    private String accountNumber;
    private String accountType;
    private String accountStatus;
    private BigDecimal availableBalance;
    private Long userId;

    // No-argument constructor
    public AccountDto() {
    }

    // All-argument constructor
    public AccountDto(Long accountId, String accountNumber, String accountType, String accountStatus,
                      BigDecimal availableBalance, Long userId) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.availableBalance = availableBalance;
        this.userId = userId;
    }

    // Getters and Setters
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
    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
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

    // Builder pattern implementation
    public static class Builder {
        private Long accountId;
        private String accountNumber;
        private String accountType;
        private String accountStatus;
        private BigDecimal availableBalance;
        private Long userId;

        public Builder accountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }
        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }
        public Builder accountType(String accountType) {
            this.accountType = accountType;
            return this;
        }
        public Builder accountStatus(String accountStatus) {
            this.accountStatus = accountStatus;
            return this;
        }
        public Builder availableBalance(BigDecimal availableBalance) {
            this.availableBalance = availableBalance;
            return this;
        }
        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }
        public AccountDto build() {
            return new AccountDto(accountId, accountNumber, accountType, accountStatus, availableBalance, userId);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
