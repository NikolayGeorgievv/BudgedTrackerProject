package com.burdettracker.budgedtrackerproject.model.dto.address;

import com.burdettracker.budgedtrackerproject.model.entity.Address;

public class AddressDto {

    private String streetName;
    private String streetNumber;
    private String city;
    private String country;

    public AddressDto() {
    }

//    public Address getAddress(){
//        Address address = new Address();
//        address.setStreetName(this.streetName);
//        address.setCity(this.city);
//        address.setCountry(this.country);
//        address.setStreetNumber(this.streetNumber);
//
//    }
//
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
