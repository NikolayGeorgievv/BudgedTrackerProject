package com.burdettracker.budgedtrackerproject.model.entity;

import com.burdettracker.budgedtrackerproject.model.entity.enums.CurrencyType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "expense_transactions")
public class ExpenseTransaction extends BaseEntity{

    @Column(name = "currency_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;
    @Column(name = "date_and_time_completed", nullable = false)
    private Date dateAndTimeCompleted;
    @Column()
    private String description;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private String paidTo;

    public ExpenseTransaction() {
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public Date getDateAndTimeCompleted() {
        return dateAndTimeCompleted;
    }

    public void setDateAndTimeCompleted(Date dateAndTimeCompleted) {
        this.dateAndTimeCompleted = dateAndTimeCompleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paid) {
        this.paidTo = paid;
    }
}
