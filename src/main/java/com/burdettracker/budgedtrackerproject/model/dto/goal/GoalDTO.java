package com.burdettracker.budgedtrackerproject.model.dto.goal;

import java.math.BigDecimal;

public class GoalDTO {

    private Long id;
    private String name;
    private BigDecimal amountToBeSaved;
    private String account;
    private BigDecimal currentAmount;
    private String description;


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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
}
