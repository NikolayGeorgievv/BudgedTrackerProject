package com.burdettracker.budgedtrackerproject.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Column
    private String name;
    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;
    @Column(name = "current_amount", nullable = false)
    private BigDecimal currentAmount;

    @OneToMany
    @JoinColumn(name = "account_id")
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

    public Account(String name, LocalDate createdOn, BigDecimal currentAmount, List<Transaction> transactionHistory, User user) {
        this.name = name;
        this.createdOn = createdOn;
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
