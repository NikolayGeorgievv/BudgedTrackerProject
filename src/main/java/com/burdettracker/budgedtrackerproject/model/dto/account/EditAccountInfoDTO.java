package com.burdettracker.budgedtrackerproject.model.dto.account;

import java.math.BigDecimal;

public class EditAccountInfoDTO {

    private Long accountId;
    private String newAccountName;
    private BigDecimal addedAmount;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getNewAccountName() {
        return newAccountName;
    }

    public void setNewAccountName(String newAccountName) {
        this.newAccountName = newAccountName;
    }

    public BigDecimal getAddedAmount() {
        return addedAmount;
    }

    public void setAddedAmount(BigDecimal addedAmount) {
        this.addedAmount = addedAmount;
    }
}
