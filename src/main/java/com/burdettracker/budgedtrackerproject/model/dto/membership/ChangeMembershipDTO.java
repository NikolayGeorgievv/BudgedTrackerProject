package com.burdettracker.budgedtrackerproject.model.dto.membership;

import jakarta.validation.constraints.NotNull;

public class ChangeMembershipDTO {

    @NotNull
    private String accountToUse;
    @NotNull
    private String membership;


    public String getAccountToUse() {
        return accountToUse;
    }

    public void setAccountToUse(String accountToUse) {
        this.accountToUse = accountToUse;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }
}
