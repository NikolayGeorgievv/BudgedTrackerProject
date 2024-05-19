package com.burdettracker.budgedtrackerproject.model.dto.user;

public class UserFullNameDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String membership;

    public UserFullNameDTO() {
    }

    public UserFullNameDTO(String firstName, String lastName,String email,String membership) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.membership = membership;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    @Override
    public String toString() {
        return firstName + " "+ lastName;
    }
}
