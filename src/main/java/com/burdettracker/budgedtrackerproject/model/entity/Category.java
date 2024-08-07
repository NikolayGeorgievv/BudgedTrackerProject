package com.burdettracker.budgedtrackerproject.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {


    private String category;
    private boolean isBasic;

    @ManyToOne
    private User user;

    public Category(String category, boolean isBasic) {
        this.category = category;
        this.isBasic = isBasic;
    }

    public Category(String category, boolean isBasic, User user) {
        this.category = category;
        this.isBasic = isBasic;
        this.user = user;
    }

    public Category() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isBasic() {
        return isBasic;
    }

    public void setBasic(boolean basic) {
        isBasic = basic;
    }
}

