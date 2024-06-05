package com.burdettracker.budgedtrackerproject.model.dto.account;

import com.burdettracker.budgedtrackerproject.model.entity.Transaction;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AccountDTO {

    private Long id;
    @Size(min = 2, max = 30,  message = "Account name length must be between 2 and 40 symbols.")
    @NotEmpty(message = "Please provide a name for your account.")
    private String name;
    private LocalDate createdOn;
    @Min(value = 0, message = "Added funds must be at least  $0")
    @Max(value = 9999999, message = "The maximum funds you can add is $9.999.999")
    private BigDecimal currentAmount;
    private List<Transaction> transactionHistory;
    private User user;

    public AccountDTO() {
    }

    public AccountDTO(String name, BigDecimal currentAmount) {
        this.name = name;
        this.currentAmount = currentAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
