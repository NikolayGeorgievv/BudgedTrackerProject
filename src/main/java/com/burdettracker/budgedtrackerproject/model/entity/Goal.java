package com.burdettracker.budgedtrackerproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "goals")
public class Goal extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(name = "amount_to_be_saved", nullable = false)
    private BigDecimal amountToBeSaved;
    @Column(name = "current_amount")
    private BigDecimal currentAmount;
    @Column
    private String description;
    @Column(name = "is_completed")
    private boolean isCompleted;
    @ManyToOne
    private User user;
    @ManyToOne
    private Account account;


    private String accountToUse;
    private LocalDate createdOn;
    private LocalDate completedOn;


    public Goal() {
    }

    public Goal(String name, BigDecimal amountToBeSaved, BigDecimal currentAmount, String description, boolean isCompleted, String accountToUse) {
        this.name = name;
        this.amountToBeSaved = amountToBeSaved;
        this.currentAmount = currentAmount;
        this.description = description;
        this.isCompleted = isCompleted;
        this.accountToUse = accountToUse;
    }

    public Goal(String name, BigDecimal amountToBeSaved, BigDecimal currentAmount, String description, User user, Account account, String accountToUse, boolean isCompleted, LocalDate createdOn, LocalDate completedOn) {
        this.name = name;
        this.amountToBeSaved = amountToBeSaved;
        this.currentAmount = currentAmount;
        this.description = description;
        this.user = user;
        this.account = account;
        this.accountToUse = accountToUse;
        this.isCompleted = isCompleted;
        this.createdOn = createdOn;
        this.completedOn = completedOn;
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

    public String getAccountToUse() {
        return accountToUse;
    }

    public void setAccountToUse(String accountToUse) {
        this.accountToUse = accountToUse;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDate completedOn) {
        this.completedOn = completedOn;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
