package org.training.fundtransfer.model.dto.request;

import java.math.BigDecimal;

public class FundTransferRequest {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;

    // No-argument constructor
    public FundTransferRequest() {
    }

    // All-argument constructor
    public FundTransferRequest(String fromAccount, String toAccount, BigDecimal amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
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

    // Builder pattern implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String fromAccount;
        private String toAccount;
        private BigDecimal amount;

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

        public FundTransferRequest build() {
            return new FundTransferRequest(fromAccount, toAccount, amount);
        }
    }
}
