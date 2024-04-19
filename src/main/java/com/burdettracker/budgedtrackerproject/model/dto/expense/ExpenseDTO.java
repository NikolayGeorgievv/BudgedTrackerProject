package com.burdettracker.budgedtrackerproject.model.dto.expense;

import com.burdettracker.budgedtrackerproject.model.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseDTO {

    private String name;
    private LocalDate dateDue;
    private BigDecimal assigned;
    private BigDecimal available;
    private User user;

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

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
