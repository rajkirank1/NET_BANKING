package org.training.transactions.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.training.transactions.model.TransactionStatus;
import org.training.transactions.model.TransactionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private String referenceId;

    private String accountId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private BigDecimal amount;

    @CreationTimestamp
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String comments;

    // No-argument constructor
    public Transaction() {
    }

    // All-argument constructor
    public Transaction(Long transactionId, String referenceId, String accountId, TransactionType transactionType,
                       BigDecimal amount, LocalDateTime transactionDate, TransactionStatus status, String comments) {
        this.transactionId = transactionId;
        this.referenceId = referenceId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.status = status;
        this.comments = comments;
    }

    // Getters and setters
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    // Builder pattern implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long transactionId;
        private String referenceId;
        private String accountId;
        private TransactionType transactionType;
        private BigDecimal amount;
        private LocalDateTime transactionDate;
        private TransactionStatus status;
        private String comments;

        public Builder transactionId(Long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder referenceId(String referenceId) {
            this.referenceId = referenceId;
            return this;
        }

        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public Builder status(TransactionStatus status) {
            this.status = status;
            return this;
        }

        public Builder comments(String comments) {
            this.comments = comments;
            return this;
        }

        public Transaction build() {
            return new Transaction(transactionId, referenceId, accountId, transactionType,
                    amount, transactionDate, status, comments);
        }
    }
}
