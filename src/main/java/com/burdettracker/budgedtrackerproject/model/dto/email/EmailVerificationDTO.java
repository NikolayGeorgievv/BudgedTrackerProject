package com.burdettracker.budgedtrackerproject.model.dto.email;

public class EmailVerificationDTO {
    private String email;
    private boolean is_role_account;

    public EmailVerificationDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIs_role_account() {
        return is_role_account;
    }

    public void setIs_role_account(boolean is_role_account) {
        this.is_role_account = is_role_account;
    }

    @Override
    public String toString() {
        return "EmailVerificationDTO{" +
                "email='" + email + '\'' +
                ", is_role_account=" + is_role_account +
                '}';
    }
}
