package com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class GoalDTO {

    private Long id;
    @NotEmpty
    @Size(min = 2, max = 30, message = "Goal name length must be between 2 and 30 symbols.")
    private String name;
    @Min(value = 0, message = "Please enter your goal target.")
    @Max(value = 99999999, message = "The maximum target for a goal is $99.999.999")
    private BigDecimal amountToBeSaved;
    private String accountToUse;
    private BigDecimal currentAmount;
    private String description;
    private boolean isCompleted;


    public GoalDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAccountToUse() {
        return accountToUse;
    }

    public void setAccountToUse(String accountToUse) {
        this.accountToUse = accountToUse;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getBarProgress() {

        double currentDouble = Double.parseDouble(currentAmount.toString());
        double currentTotal = Double.parseDouble(String.valueOf(amountToBeSaved));
        double resultDouble = currentDouble / currentTotal * 100;

        return (int) resultDouble;
    }
}
