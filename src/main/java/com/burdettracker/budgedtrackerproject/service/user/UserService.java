package com.burdettracker.budgedtrackerproject.service.user;


import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.membership.ChangeMembershipDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.*;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    void registerUser(RegisterUserDTO registerUserDTO);

    Authentication login(String email);

    UserExpensesDetailsDTO getUserExpensesDetailsByEmail(String email);

    void addAccount(String currentUserName, AccountDTO accountDTO);

    void addExpense(String currentUserName, ExpenseDTO expenseDTO);

    void addGoal(String currentUserName, GoalDTO goalDTO);

    void changeUserPlan(ChangeMembershipDTO changePlanDTO, String email);

    AllUsersInfoDTO getAllUsersInfo();

    AllUsersInfoDTO filterAllUsersByEmail(String email);

    UserFullDetailsInfoDTO getUserById(String userId);

    void updateUser(UserChangeInformationDTO userChangeInformationDTO);

    User getUserByEmail(String email);

    void saveAndFlush(User user);
}
