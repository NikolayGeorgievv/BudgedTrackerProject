package com.burdettracker.budgedtrackerproject.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "goals")
public class Goal extends BaseEntity{

    @Column(nullable = false)
    private String name;
    @Column(name = "amount_to_be_saved", nullable = false)
    private BigDecimal amountToBeSaved;
    @Column(name = "current_amount")
    private BigDecimal currentAmount;
    @Column
    private String description;
    @ManyToOne
    private User user;
    @ManyToOne
    private Account account;

    public Goal() {
    }

    public Goal(String name, BigDecimal amountToBeSaved, BigDecimal currentAmount, String description, User user, Account account) {
        this.name = name;
        this.amountToBeSaved = amountToBeSaved;
        this.currentAmount = currentAmount;
        this.description = description;
        this.user = user;
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
