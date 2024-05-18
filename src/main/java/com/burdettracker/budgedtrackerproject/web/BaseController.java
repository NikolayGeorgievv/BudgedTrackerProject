package com.burdettracker.budgedtrackerproject.web;


import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.completed.AllCompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.AllUncompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.AccountTransactionsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserFullNameDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
public class BaseController {

    protected List<ExpenseDTO> expenses;
    protected final UserService userService;
    protected final ExpenseService expenseService;
    protected final AccountService accountService;
    protected List<AccountDTO> accounts;
    protected final GoalsService goalsService;
    protected final TransactionService transactionService;


    public BaseController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService, GoalsService goalsService, TransactionService transactionService) {
        this.expenses = expenses;
        this.userService = userService;
        this.expenseService = expenseService;
        this.accountService = accountService;
        this.goalsService = goalsService;
        this.transactionService = transactionService;
    }

    @ModelAttribute("allCompletedGoalsInfoDTO")
    public AllCompletedGoalsInfoDTO allCompletedGoalsInfoDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return this.goalsService.getAllCompletedGoals(currentUserName);
    }

    @ModelAttribute("allUncompletedGoalsInfoDTO")
    public AllUncompletedGoalsInfoDTO allGoalsInfoDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return this.goalsService.getAllUncompletedGoals(currentUserName);
    }

    @ModelAttribute("allAccountsInfoDTO")
    public AllAccountsInfoDTO allAccountsInfoDTO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return this.accountService.getAllAccounts(currentUserName);
    }

    @ModelAttribute("userExpenses")
    public Model userExpenses(Model model) {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();
        expenses = userByEmail.getExpenses();
        model.addAttribute("userExpenses", expenses);
        return model;
    }

    @ModelAttribute("userFullNameDTO")
    public UserFullNameDTO userFullNameDTO() {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        return new UserFullNameDTO(userByEmail.getFirstName(), userByEmail.getLastName(), userByEmail.getEmail());
    }

    @ModelAttribute("expenseDTO")
    public ExpenseDTO expenseDTO() {
        return new ExpenseDTO();
    }

    @ModelAttribute("userAccounts")
    public Model userAccountsModel(Model model) {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        accounts = userByEmail.getAccounts();
        model.addAttribute("userAccounts", accounts);
        return model;
    }

    @ModelAttribute("usersAccountCeil")
    public Model accountCeilModel(Model model) {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();
        if (userByEmail.getAccounts().size() != userByEmail.getUserAccountsAllowed()) {
            model.addAttribute("usersAccountCeil", true);
        }
        return model;
    }

    @ModelAttribute("accountDTO")
    public AccountDTO accountDTO() {
        return new AccountDTO();
    }

    public UserExpensesDetailsDTO getUserByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserByEmail(currentUserName);

    }

    @ModelAttribute("totalExpensesFunds")
    public String totalExpensesFunds() {
        double totalBalance = expenses.stream().mapToDouble(e -> Double.parseDouble(String.valueOf(e.getAssigned()))).sum();
        return String.valueOf(totalBalance);
    }

    @ModelAttribute("todaysDate")
    public String todaysDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String day = String.valueOf(LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US));
        return date.format(formatter) + " (" + day + ")";
    }
}
