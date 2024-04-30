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
import java.util.Random;

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

    @ModelAttribute("userFullNameDTO")
    public UserFullNameDTO userFullNameDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        UserExpensesDetailsDTO userByEmail = userService.getUserByEmail(currentUserName);

        return new UserFullNameDTO(userByEmail.getFirstName(), userByEmail.getLastName(), currentUserName);
    }

    @ModelAttribute
    @GetMapping("/index")
    public String loggedIn(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        UserExpensesDetailsDTO userByEmail = userService.getUserByEmail(currentUserName);

        expenses = userByEmail.getExpenses();
        model.addAttribute("userExpenses", expenses);

        if (userByEmail.getAccounts().size() != userByEmail.getUserAccountsAllowed()){
            model.addAttribute("usersAccountCeil", true);
        }

        accounts = userByEmail.getAccounts();
        model.addAttribute("userAccounts", accounts);

        //Use this random number to initialize date-pickers for each row.
        int random = new Random().nextInt();
        model.addAttribute("randomNumber", random);

        return "index";
    }
}
