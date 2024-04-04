package com.burdettracker.budgedtrackerproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_documents")
public class UserDocument extends BaseEntity{

    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    private String url;

    public UserDocument() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
