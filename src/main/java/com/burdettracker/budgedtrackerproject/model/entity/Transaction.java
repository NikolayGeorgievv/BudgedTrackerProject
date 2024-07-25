package com.burdettracker.budgedtrackerproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {


    @Column(nullable = false)
    private String paidTo;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(name = "completed_on")
    private LocalDate completedOn;
    @ManyToOne
    private Account account;
    @ManyToOne
    private User user;

    private String transactionDescription;

    public Transaction(String paidTo, BigDecimal amount, LocalDate completedOn, Account account) {
        this.paidTo = paidTo;
        this.amount = amount;
        this.completedOn = completedOn;
        this.account = account;
    }

    public Transaction() {

    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDate completedOn) {
        this.completedOn = completedOn;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
