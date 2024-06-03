package com.burdettracker.budgedtrackerproject.model.dto.user;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.entity.*;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.model.entity.enums.UserRoleEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class UserExpensesDetailsDTO {

    private UUID id;
    private MembershipType membershipType;
    private String phoneNumber;
    private BigDecimal totalBalance;
    private List<AccountDTO> accounts;
    private List<Goal> goals;
    private String firstName;
    private String lastName;
    private String email;
    private List<ExpenseDTO> expenses;
    private int userAccountsAllowed;
    private String accountNameAssignedForSubscription;

    public UserExpensesDetailsDTO() {
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getUserAccountsAllowed() {
        return userAccountsAllowed;
    }

    public void setUserAccountsAllowed(int userAccountsAllowed) {
        this.userAccountsAllowed = userAccountsAllowed;
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

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    public String getAccountNameAssignedForSubscription() {
        return accountNameAssignedForSubscription;
    }

    public void setAccountNameAssignedForSubscription(String accountNameAssignedForSubscription) {
        this.accountNameAssignedForSubscription = accountNameAssignedForSubscription;
    }
}
