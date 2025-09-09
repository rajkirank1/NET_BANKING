package org.training.user.service.model.dto.external;

import java.math.BigDecimal;

public class AccountResponse {

    private String accountNumber;
    private BigDecimal actualBalance;
    private Integer id;
    private String type;
    private String status;
    private BigDecimal availableBalance;

    // No-args constructor
    public AccountResponse() {
    }

    // All-args constructor
    public AccountResponse(String accountNumber, BigDecimal actualBalance, Integer id,
                           String type, String status, BigDecimal availableBalance) {
        this.accountNumber = accountNumber;
        this.actualBalance = actualBalance;
        this.id = id;
        this.type = type;
        this.status = status;
        this.availableBalance = availableBalance;
    }

    // Getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }
}
