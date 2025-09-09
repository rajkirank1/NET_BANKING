package org.training.fundtransfer.model.dto;

import org.training.fundtransfer.model.TransactionStatus;
import org.training.fundtransfer.model.TransferType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FundTransferDto {

    private String transactionReference;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private TransactionStatus status;
    private TransferType transferType;
    private LocalDateTime transferredOn;

    public FundTransferDto() {
    }

    public FundTransferDto(String transactionReference, String fromAccount, String toAccount,
                           BigDecimal amount, TransactionStatus status, TransferType transferType,
                           LocalDateTime transferredOn) {
        this.transactionReference = transactionReference;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = status;
        this.transferType = transferType;
        this.transferredOn = transferredOn;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String transactionReference;
        private String fromAccount;
        private String toAccount;
        private BigDecimal amount;
        private TransactionStatus status;
        private TransferType transferType;
        private LocalDateTime transferredOn;

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
        public FundTransferDto build() {
            return new FundTransferDto(transactionReference, fromAccount, toAccount, amount, status, transferType, transferredOn);
        }
    }
}
