package org.training.fundtransfer.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.training.fundtransfer.model.TransactionStatus;
import org.training.fundtransfer.model.TransferType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class FundTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundTransferId;

    private String transactionReference;

    private String fromAccount;

    private String toAccount;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @CreationTimestamp
    private LocalDateTime transferredOn;

    // No-argument constructor
    public FundTransfer() {
    }

    // All-argument constructor
    public FundTransfer(Long fundTransferId, String transactionReference, String fromAccount, String toAccount,
                        BigDecimal amount, TransactionStatus status, TransferType transferType,
                        LocalDateTime transferredOn) {
        this.fundTransferId = fundTransferId;
        this.transactionReference = transactionReference;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = status;
        this.transferType = transferType;
        this.transferredOn = transferredOn;
    }

    // Getters and setters
    public Long getFundTransferId() {
        return fundTransferId;
    }

    public void setFundTransferId(Long fundTransferId) {
        this.fundTransferId = fundTransferId;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public LocalDateTime getTransferredOn() {
        return transferredOn;
    }

    public void setTransferredOn(LocalDateTime transferredOn) {
        this.transferredOn = transferredOn;
    }

    // Builder implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long fundTransferId;
        private String transactionReference;
        private String fromAccount;
        private String toAccount;
        private BigDecimal amount;
        private TransactionStatus status;
        private TransferType transferType;
        private LocalDateTime transferredOn;

        public Builder fundTransferId(Long fundTransferId) {
            this.fundTransferId = fundTransferId;
            return this;
        }

        public Builder transactionReference(String transactionReference) {
            this.transactionReference = transactionReference;
            return this;
        }

        public Builder fromAccount(String fromAccount) {
            this.fromAccount = fromAccount;
            return this;
        }

        public Builder toAccount(String toAccount) {
            this.toAccount = toAccount;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder status(TransactionStatus status) {
            this.status = status;
            return this;
        }

        public Builder transferType(TransferType transferType) {
            this.transferType = transferType;
            return this;
        }

        public Builder transferredOn(LocalDateTime transferredOn) {
            this.transferredOn = transferredOn;
            return this;
        }

        public FundTransfer build() {
            return new FundTransfer(fundTransferId, transactionReference, fromAccount, toAccount,
                    amount, status, transferType, transferredOn);
        }
    }
}
