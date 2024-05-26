package com.burdettracker.budgedtrackerproject.model.dto.user;

import jakarta.validation.constraints.Size;

public class UserChangeInformationDTO {

    private String id;
    @Size(min = 3, max = 20)
    private String newFirstName;
    @Size(min = 3, max = 20)
    private String newLastName;
    @Size(min = 3, max = 20)
    private String newPhoneNumber;
    private boolean demoteAdmin;
    private boolean promoteUser;
    private String membership;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewFirstName() {
        return newFirstName;
    }

    public void setNewFirstName(String newFirstName) {
        this.newFirstName = newFirstName;
    }

    public String getNewLastName() {
        return newLastName;
    }

    public void setNewLastName(String newLastName) {
        this.newLastName = newLastName;
    }

    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }

    public boolean isDemoteAdmin() {
        return demoteAdmin;
    }

    public void setDemoteAdmin(boolean demoteAdmin) {
        this.demoteAdmin = demoteAdmin;
    }

    public boolean isPromoteUser() {
        return promoteUser;
    }

    public void setPromoteUser(boolean promoteUser) {
        this.promoteUser = promoteUser;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }
}
