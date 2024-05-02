package com.burdettracker.budgedtrackerproject.model.dto.account;

import java.math.BigDecimal;

public class EditAccountInfoDTO {

    private String id;
    private String newAccountName;
    private BigDecimal addedAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
