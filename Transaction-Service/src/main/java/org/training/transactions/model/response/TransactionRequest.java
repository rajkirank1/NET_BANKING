package org.training.transactions.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRequest {

    private String referenceId;
    private String accountId;
    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime localDateTime;
    private String transactionStatus;
    private String comments;

    // No-argument constructor
    public TransactionRequest() {
    }

    // All-argument constructor
    public TransactionRequest(String referenceId, String accountId, String transactionType, BigDecimal amount,
                              LocalDateTime localDateTime, String transactionStatus, String comments) {
        this.referenceId = referenceId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.localDateTime = localDateTime;
        this.transactionStatus = transactionStatus;
        this.comments = comments;
    }

    public String getReferenceId() {
        return referenceId;
    }
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String referenceId;
        private String accountId;
        private String transactionType;
        private BigDecimal amount;
        private LocalDateTime localDateTime;
        private String transactionStatus;
        private String comments;

        public Builder referenceId(String referenceId) {
            this.referenceId = referenceId;
            return this;
        }
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
        public Builder localDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
            return this;
        }
        public Builder transactionStatus(String transactionStatus) {
            this.transactionStatus = transactionStatus;
            return this;
        }
        public Builder comments(String comments) {
            this.comments = comments;
            return this;
        }

        public TransactionRequest build() {
            return new TransactionRequest(referenceId, accountId, transactionType, amount, localDateTime, transactionStatus, comments);
        }
    }
}
