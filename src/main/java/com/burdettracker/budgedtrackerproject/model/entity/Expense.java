package com.burdettracker.budgedtrackerproject.model.entity;

import jakarta.persistence.*;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

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
    @Column(nullable = false)
    private BigDecimal available;
    @ManyToOne
    private User user;

    public Expense(String name, LocalDate dateDue, BigDecimal assigned, BigDecimal available, User user) {
        this.name = name;
        this.dateDue = dateDue;
        this.assigned = assigned;
        this.available = available;
        this.user = user;
    }

    public Expense() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }
}
