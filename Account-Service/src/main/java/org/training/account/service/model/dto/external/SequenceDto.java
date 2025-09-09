package org.training.account.service.model.dto.external;

public class SequenceDto {

    private long sequenceId;
    private long accountNumber;

    // No-argument constructor
    public SequenceDto() {
    }

    // All-argument constructor
    public SequenceDto(long sequenceId, long accountNumber) {
        this.sequenceId = sequenceId;
        this.accountNumber = accountNumber;
    }

    // Getter for sequenceId
    public long getSequenceId() {
        return sequenceId;
    }

    // Setter for sequenceId
    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    // Getter for accountNumber
    public long getAccountNumber() {
        return accountNumber;
    }

    // Setter for accountNumber
    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }
}
