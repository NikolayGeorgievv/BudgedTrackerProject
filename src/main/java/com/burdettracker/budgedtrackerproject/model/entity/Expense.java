package com.burdettracker.budgedtrackerproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expense extends BaseEntity{

    @Column(nullable = false)
    private String name;
    @Column(name = "date_due")
    private LocalDate dateDue;
    @Column(nullable = false)
    private BigDecimal assigned;
    @Column
    private String period;

    @Column(name = "period_date")
    private String periodDate;

    @ManyToOne
    private Account accountToUse;


    public Expense() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateDue() {
        return dateDue;
    }

    public void setDateDue(LocalDate dateDue) {
        this.dateDue = dateDue;
    }

    public BigDecimal getAssigned() {
        return assigned;
    }

    public void setAssigned(BigDecimal assigned) {
        this.assigned = assigned;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Account getAccountToUse() {
        return accountToUse;
    }

    public void setAccountToUse(Account accountToUse) {
        this.accountToUse = accountToUse;
    }

    public String getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(String periodDate) {
        this.periodDate = periodDate;
    }
}
