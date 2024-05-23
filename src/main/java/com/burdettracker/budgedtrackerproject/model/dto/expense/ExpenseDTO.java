package com.burdettracker.budgedtrackerproject.model.dto.expense;

import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ExpenseDTO {

    private String id;
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
    private String category;

    private User user;
    private Account account;

    public ExpenseDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccountToUse() {
        return accountToUse;
    }

    public void setAccountToUse(String accountToUse) {
        this.accountToUse = accountToUse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
