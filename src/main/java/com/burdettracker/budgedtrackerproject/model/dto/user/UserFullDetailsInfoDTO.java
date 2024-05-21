package com.burdettracker.budgedtrackerproject.model.dto.user;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;
import com.burdettracker.budgedtrackerproject.model.entity.UserDocument;
import com.burdettracker.budgedtrackerproject.model.entity.UserRoleEntity;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;

import java.util.List;
import java.util.UUID;

public class UserFullDetailsInfoDTO {

    private UUID id;
    private MembershipType membershipType;
    private String phoneNumber;
    private List<AccountDTO> accounts;
    private List<UserDocument> userDocuments;
    private List<Goal> goals;
    private String firstName;
    private String lastName;
    private String email;
    private List<ExpenseDTO> expenses;
    private int userAccountsAllowed;
    private String accountNameAssignedForSubscription;
    private List<UserRoleEntity>  roles;
    private String registeredOnDate;

    public UserFullDetailsInfoDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public List<UserDocument> getUserDocuments() {
        return userDocuments;
    }

    public void setUserDocuments(List<UserDocument> userDocuments) {
        this.userDocuments = userDocuments;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
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

    public List<ExpenseDTO> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDTO> expenses) {
        this.expenses = expenses;
    }

    public int getUserAccountsAllowed() {
        return userAccountsAllowed;
    }

    public void setUserAccountsAllowed(int userAccountsAllowed) {
        this.userAccountsAllowed = userAccountsAllowed;
    }

    public String getAccountNameAssignedForSubscription() {
        return accountNameAssignedForSubscription;
    }

    public void setAccountNameAssignedForSubscription(String accountNameAssignedForSubscription) {
        this.accountNameAssignedForSubscription = accountNameAssignedForSubscription;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }

    public String getRegisteredOnDate() {
        return registeredOnDate;
    }

    public void setRegisteredOnDate(String registeredOnDate) {
        this.registeredOnDate = registeredOnDate;
    }

    public String getAllRoles(){
        StringBuilder sb = new StringBuilder();
        this.getRoles().forEach(r ->{
            String role = r.getRole().name();
            sb.append(role);
            sb.append(" ");
        });
        return sb.toString();
    }
}
