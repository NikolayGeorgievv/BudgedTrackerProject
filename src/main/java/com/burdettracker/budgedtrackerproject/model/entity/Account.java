package com.burdettracker.budgedtrackerproject.model.entity;

import com.burdettracker.budgedtrackerproject.model.entity.enums.CurrencyType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{

    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;
    @Column(name = "currency_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;
    @Column(name = "current_amount", nullable = false)
    private BigDecimal currentAmount;
    @OneToMany
    private List<IncomeTransaction> incomeTransactionHistory;
    @OneToMany
    private List<ExpenseTransaction> expenseTransactionHistory;

    public Account() {
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

    public List<IncomeTransaction> getIncomeTransactionHistory() {
        return incomeTransactionHistory;
    }

    public void setIncomeTransactionHistory(List<IncomeTransaction> incomeTransactionHistory) {
        this.incomeTransactionHistory = incomeTransactionHistory;
    }

    public List<ExpenseTransaction> getExpenseTransactionHistory() {
        return expenseTransactionHistory;
    }

    public void setExpenseTransactionHistory(List<ExpenseTransaction> expenseTransactionHistory) {
        this.expenseTransactionHistory = expenseTransactionHistory;
    }
}
