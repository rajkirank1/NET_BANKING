package org.training.sequence.generator.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sequenceId;

    private long accountNumber;

    // No-argument constructor
    public Sequence() {
    }

    // All-argument constructor
    public Sequence(long sequenceId, long accountNumber) {
        this.sequenceId = sequenceId;
        this.accountNumber = accountNumber;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Builder implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long sequenceId;
        private long accountNumber;

        public Builder sequenceId(long sequenceId) {
            this.sequenceId = sequenceId;
            return this;
        }

        public Builder accountNumber(long accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Sequence build() {
            return new Sequence(sequenceId, accountNumber);
        }
    }
}
