package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserFullNameDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
public class MainPageController {

    private final UserService userService;
    private List<ExpenseDTO> expenses;
    private List<AccountDTO> accounts;
    private final AccountService accountService;

    public MainPageController(UserService userService, List<ExpenseDTO> expenses, List<AccountDTO> accounts, AccountService accountService) {
        this.userService = userService;
        this.expenses = expenses;
        this.accounts = accounts;
        this.accountService = accountService;
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
    @ModelAttribute("allAccountsInfoDTO")
    public AllAccountsInfoDTO allAccountsInfoDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return this.accountService.getAllAccounts(currentUserName);
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

        double totalBalance = expenses.stream().mapToDouble(e -> Double.parseDouble(String.valueOf(e.getAssigned()))).sum();
        model.addAttribute("totalExpensesFunds", totalBalance);

        model.addAttribute("todaysDate", todaysDate());

        return "redirect:/index";
    }

    public UserExpensesDetailsDTO getUserByEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserByEmail(currentUserName);

    }
    public String todaysDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String day = String.valueOf(LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US));
        return date.format(formatter) + " (" + day + ")";
    }
}
