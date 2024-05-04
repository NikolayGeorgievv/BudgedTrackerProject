package com.burdettracker.budgedtrackerproject.model.dto.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseDTO {

    private String name;
    private String period;
    private String dateDue;
    private BigDecimal assigned;
    private BigDecimal available;
    private String accountToUse;
    private String periodDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(String periodDate) {
        this.periodDate = periodDate;
    }

    public BigDecimal getAssigned() {
        return assigned;
    }

    public void setAssigned(BigDecimal assigned) {
        this.assigned = assigned;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAccountToUse() {
        return accountToUse;
    }

    public void setAccountToUse(String accountToUse) {
        this.accountToUse = accountToUse;
    }
}
