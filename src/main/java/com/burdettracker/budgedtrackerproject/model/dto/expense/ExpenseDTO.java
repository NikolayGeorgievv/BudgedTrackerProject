package com.burdettracker.budgedtrackerproject.model.dto.expense;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ExpenseDTO {

    @NotEmpty(message = "Please enter a name.")
    private String name;
    @NotEmpty(message = "Please choose a period.")
    private String period;
    private String dateDue;
    @Positive(message = "Please enter the funds needed.")
    @NotNull(message = "Please enter the funds needed.")
    private BigDecimal assigned;
    @NotEmpty(message = "Please choose an account.")
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
