package com.burdettracker.budgedtrackerproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "goals")
public class Goal extends BaseEntity{

    @Column(nullable = false)
    private String name;
    @Column(name = "amount_to_be_saved", nullable = false)
    private BigDecimal amountToBeSaved;
    @Column
    private String description;

    public Goal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmountToBeSaved() {
        return amountToBeSaved;
    }

    public void setAmountToBeSaved(BigDecimal amountToBeSaved) {
        this.amountToBeSaved = amountToBeSaved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
