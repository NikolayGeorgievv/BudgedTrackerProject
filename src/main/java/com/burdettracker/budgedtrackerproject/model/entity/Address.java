package com.burdettracker.budgedtrackerproject.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    @Column(name = "street_name", nullable = false)
    private String streetName;
    @Column(name = "street_number")
    private String streetNumber;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country", nullable = false)
    private String country;


    @OneToMany
    @JoinColumn(name = "address")
    private List<User> user;

    public Address() {
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
