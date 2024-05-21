package com.burdettracker.budgedtrackerproject.model.dto.user;

public class UserChangeInformationDTO {

    private String id;
    private String newFirstName;
    private String newLastName;
    private String newPhoneNumber;
    private boolean demoteAdmin;
    private boolean promoteUser;


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
}
