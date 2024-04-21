package com.burdettracker.budgedtrackerproject.model.entity;


import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User{

    @NotNull
    @Id
    @UuidGenerator
    private UUID id;
    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    @Column(name = "total_balance", nullable = false)
    private BigDecimal totalBalance = BigDecimal.ZERO;
    @Column(name = "password", nullable = false)
    private String password;
    private int userAccountsAllowed;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Account> accounts = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserDocument> userDocuments = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Goal> goals = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Expense> expenses = new ArrayList<>();

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
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

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance() {
        this.totalBalance = BigDecimal.ZERO;
    }

    public int getUserAccountsAllowed() {
        return userAccountsAllowed;
    }

    public void setUserAccountsAllowed(int userAccountsAllowed) {
        this.userAccountsAllowed = userAccountsAllowed;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public void setAccounts(List<Account> accounts) {
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

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
