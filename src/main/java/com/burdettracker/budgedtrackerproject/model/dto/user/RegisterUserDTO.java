package com.burdettracker.budgedtrackerproject.model.dto.user;

import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;

public class RegisterUserDTO {

    @NotEmpty(message = "Please enter a first name.")
    @Size(min = 3, max = 20)
    private String firstName;
    @NotEmpty(message = "Please enter a last name.")
    @Size(min = 3, max = 20)
    private String lastName;
    @NotEmpty(message = "Please enter an email.")
    @Email
    private String email;
    @NotEmpty(message = "Please enter a phone number.")
    @Size(max = 20)
    private String phoneNumber;
    @NotEmpty(message = "Please enter a password.")
    @Size(min = 8, max = 40)
    private String password;
    @NotEmpty
    private String confirmPassword;
    private MembershipType membership;
    @AssertTrue
    private boolean termsAccepted;

    public boolean isTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public MembershipType getMembership() {
        return membership;
    }

    public void setMembership(MembershipType membership) {
        this.membership = membership;
    }

    public RegisterUserDTO() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
