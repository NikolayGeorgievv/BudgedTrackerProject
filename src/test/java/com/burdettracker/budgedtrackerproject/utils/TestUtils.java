package com.burdettracker.budgedtrackerproject.utils;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.entity.*;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.model.entity.enums.UserRoleEnum;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TestUtils {


    public static User createDummyUser(RolesRepository rolesRepository) {
        UUID uuid = new UUID(5, 10);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRole(UserRoleEnum.ADMIN);
        User user = new User(uuid,
                MembershipType.PREMIUM,
                "firstName",
                "lastName",
                "myEmail@example.com",
                "phoneNumber",
                "test",
                10,
                LocalDate.now(),
                "MyAccount",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                List.of(userRoleEntity));
        rolesRepository.saveAllAndFlush(List.of(userRoleEntity));

        return user;
    }

    public static Goal createDummyGoal() {
        return new Goal("goalName", BigDecimal.valueOf(100), BigDecimal.valueOf(50), "description", false, "myAccount");
    }

    public static Account createDummyAccount() {
        Account account = new Account();
        account.setName("MyTestAcc");
        account.setCreatedOn(LocalDate.now());
        account.setCurrentAmount(BigDecimal.ZERO);

        return account;
    }

    public static Expense createDummyExpense() {
        Expense expense = new Expense();
        expense.setName("expenseName");
        expense.setAssigned(BigDecimal.valueOf(100));
        expense.setPeriod("monthly");
        expense.setPeriodDate("21st");
        return expense;
    }

    public static ExpenseDTO createDummyExpenseDTO() {
        return new ExpenseDTO("1", "expenseName", "monthly", "", BigDecimal.valueOf(100), "GENERAL", "MyTestAcc", "21st");
    }
}
