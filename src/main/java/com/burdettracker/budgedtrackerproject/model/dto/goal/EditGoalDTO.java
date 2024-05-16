package com.burdettracker.budgedtrackerproject.model.dto.goal;

import java.math.BigDecimal;

public class EditGoalDTO {

    private String id;
    private String newGoalName;
    private BigDecimal addedAmount;
    private String accountToUse;
    private String isNewPrimary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewGoalName() {
        return newGoalName;
    }

    public void setNewGoalName(String newGoalName) {
        this.newGoalName = newGoalName;
    }

    public BigDecimal getAddedAmount() {
        return addedAmount;
    }

    public void setAddedAmount(BigDecimal addedAmount) {
        this.addedAmount = addedAmount;
    }

    public String getAccountToUse() {
        return accountToUse;
    }

    public void setAccountToUse(String accountToUse) {
        this.accountToUse = accountToUse;
    }

    public String getIsNewPrimary() {
        return isNewPrimary;
    }

    public void setIsNewPrimary(String isNewPrimary) {
        this.isNewPrimary = isNewPrimary;
    }
}
