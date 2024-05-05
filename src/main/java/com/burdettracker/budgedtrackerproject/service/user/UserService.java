package com.burdettracker.budgedtrackerproject.service.user;


import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import org.springframework.security.core.Authentication;

public interface UserService {
    void registerUser(RegisterUserDTO registerUserDTO);

    Authentication login(String email);

    UserExpensesDetailsDTO getUserByEmail(String email);


    void addAccount(String currentUserName, AccountDTO accountDTO);

    void addExpense(String currentUserName, ExpenseDTO expenseDTO);
}
