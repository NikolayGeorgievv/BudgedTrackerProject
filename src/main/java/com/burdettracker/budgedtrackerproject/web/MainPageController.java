package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainPageController extends BaseController{

    private final UserService userService;
    private List<AccountDTO> accounts;
    private List<ExpenseDTO> expenses;
    private final AccountService accountService;

    public MainPageController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService, AccountService accountService1, UserService userService1, List<AccountDTO> accounts, List<ExpenseDTO> expenses1, AccountService accountService2) {
        super(expenses, userService, expenseService, accountService, accountService1);
        this.userService = userService1;
        this.accounts = accounts;
        this.expenses = expenses1;
        this.accountService = accountService2;
    }

    @ModelAttribute
    @GetMapping("/index")
    public String loggedIn(Model model) {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        if (userByEmail.getAccounts().size() != userByEmail.getUserAccountsAllowed()){
            model.addAttribute("usersAccountCeil", true);
        }

        return "redirect:/index";
    }

    public UserExpensesDetailsDTO getUserByEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserByEmail(currentUserName);

    }

}
