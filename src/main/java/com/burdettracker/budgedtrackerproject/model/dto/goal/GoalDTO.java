package com.burdettracker.budgedtrackerproject.model.dto.goal;

import java.math.BigDecimal;

public class GoalDTO {

    private String name;
    private BigDecimal amountToBeSaved;
    private String goalAccountToUse;
    private BigDecimal initialAmount;
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmountToBeSaved() {
        return amountToBeSaved;
    }

    public void setAmountToBeSaved(BigDecimal amountToBeSaved) {
        this.amountToBeSaved = amountToBeSaved;
    }

    public String getGoalAccountToUse() {
        return goalAccountToUse;
    }

    public void setGoalAccountToUse(String goalAccountToUse) {
        this.goalAccountToUse = goalAccountToUse;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
