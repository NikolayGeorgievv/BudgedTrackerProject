package com.burdettracker.budgedtrackerproject.model.entity;

import com.burdettracker.budgedtrackerproject.model.entity.enums.CurrencyType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{

    @Column
    private String name;
    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;
    @Column(name = "currency_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;
    @Column(name = "current_amount", nullable = false)
    private BigDecimal currentAmount;
    @OneToMany
    private List<Transaction> transactionHistory;
    @ManyToOne
    private User user;
    @OneToMany
    @JoinColumn(name = "account_id")
    private List<Expense> expenses = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "account_id")
    private List<Goal> goals = new ArrayList<>();


    public Account() {
    }

    public Account(String name,LocalDate createdOn, CurrencyType currencyType, BigDecimal currentAmount, List<Transaction> transactionHistory, User user) {
        this.name = name;
        this.createdOn = createdOn;
        this.currencyType = currencyType;
        this.currentAmount = currentAmount;
        this.transactionHistory = transactionHistory;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }


    public List<Transaction> getExpenseTransactionHistory() {
        return transactionHistory;
    }

    public void setExpenseTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
}
