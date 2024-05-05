package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserFullNameDTO;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainPageController {

    private final UserService userService;
    private List<ExpenseDTO> expenses;
    private List<AccountDTO> accounts;

    public MainPageController(UserService userService, List<ExpenseDTO> expenses, List<AccountDTO> accounts) {
        this.userService = userService;
        this.expenses = expenses;
        this.accounts = accounts;
    }

    @ModelAttribute("accountDTO")
    public AccountDTO accountDTO(){
        return new AccountDTO();
    }

    @ModelAttribute("expenseDTO")
    public ExpenseDTO expenseDTO(){
        return new ExpenseDTO();
    }

    @ModelAttribute("userFullNameDTO")
    public UserFullNameDTO userFullNameDTO(){
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        return new UserFullNameDTO(userByEmail.getFirstName(), userByEmail.getLastName(), userByEmail.getEmail());
    }

    @ModelAttribute
    @GetMapping("/index")
    public String loggedIn(Model model) {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        expenses = userByEmail.getExpenses();
        model.addAttribute("userExpenses", expenses);

        if (userByEmail.getAccounts().size() != userByEmail.getUserAccountsAllowed()){
            model.addAttribute("usersAccountCeil", true);
        }

        accounts = userByEmail.getAccounts();
        model.addAttribute("userAccounts", accounts);

        return "index";
    }

    public UserExpensesDetailsDTO getUserByEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserByEmail(currentUserName);

    }
}
