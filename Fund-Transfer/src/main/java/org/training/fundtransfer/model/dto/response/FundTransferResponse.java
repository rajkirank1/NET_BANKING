package org.training.fundtransfer.model.dto.response;

public class FundTransferResponse {

    private String transactionId;

    private String message;

    // No-argument constructor
    public FundTransferResponse() {
    }

    // All-argument constructor
    public FundTransferResponse(String transactionId, String message) {
        this.transactionId = transactionId;
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String transactionId;
        private String message;

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public FundTransferResponse build() {
            return new FundTransferResponse(transactionId, message);
        }
    }
}
