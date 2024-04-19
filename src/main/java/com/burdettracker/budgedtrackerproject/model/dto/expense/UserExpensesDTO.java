package com.burdettracker.budgedtrackerproject.model.dto.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserExpensesDTO {

    private String name;
    private BigDecimal assigned;
    private LocalDate dateDue;
    private BigDecimal available;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAssigned() {
        return assigned;
    }

    public void setAssigned(BigDecimal assigned) {
        this.assigned = assigned;
    }

    public LocalDate getDateDue() {
        return dateDue;
    }

    public void setDateDue(LocalDate dateDue) {
        this.dateDue = dateDue;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }
}
