package org.training.fundtransfer.model.dto;

import java.math.BigDecimal;

public class Transaction {

    private String accountId;
    private String transactionType;
    private BigDecimal amount;
    private String description;

    public Transaction() {}

    public Transaction(String accountId, String transactionType, BigDecimal amount, String description) {
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
    }

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String accountId;
        private String transactionType;
        private BigDecimal amount;
        private String description;

        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }
        public Builder transactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Transaction build() {
            return new Transaction(accountId, transactionType, amount, description);
        }
    }
}
