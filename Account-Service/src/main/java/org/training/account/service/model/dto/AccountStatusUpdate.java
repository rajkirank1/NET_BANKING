package org.training.account.service.model.dto;

import org.training.account.service.model.AccountStatus;

public class AccountStatusUpdate {

    private AccountStatus accountStatus;

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
