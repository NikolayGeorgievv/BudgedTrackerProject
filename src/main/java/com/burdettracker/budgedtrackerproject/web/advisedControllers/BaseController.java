package com.burdettracker.budgedtrackerproject.web.advisedControllers;


import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.completed.AllCompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.AllUncompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.membership.ChangeMembershipDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.TransactionInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.AllUsersInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserFullNameDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Category;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.category.CategoryService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@ControllerAdvice(basePackages = "com.burdettracker.budgedtrackerproject.web.advisedControllers")
public class BaseController {

    protected final UserService userService;
    protected final ExpenseService expenseService;
    protected final AccountService accountService;
    protected final GoalsService goalsService;
    protected final TransactionService transactionService;
    protected final CategoryService categoryService;


    public BaseController(UserService userService, ExpenseService expenseService, AccountService accountService, GoalsService goalsService, TransactionService transactionService, CategoryService categoryService) {
        this.userService = userService;
        this.expenseService = expenseService;
        this.accountService = accountService;
        this.goalsService = goalsService;
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("goalDTO")
    public GoalDTO goalDTO() {
        return new GoalDTO();
    }

    @ModelAttribute("addCategoryDTO")
    public AddCategoryDTO addCategoryDTO() {
        return new AddCategoryDTO();
    }

    @ModelAttribute("allTransactions")
    public List<TransactionInfoDTO> allTransactionsDTO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return transactionService.getAllTransactionsInfo(currentUserName);
    }

    @ModelAttribute("expenseCategories")
    public List<Category> getAllCategories() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return categoryService.getAllCategories(currentUserName);
    }

    @ModelAttribute("allUsersDTO")
    public AllUsersInfoDTO allUsersDTO() {
        return this.userService.getAllUsersInfo();
    }

    @ModelAttribute("allCompletedGoalsInfoDTO")
    public AllCompletedGoalsInfoDTO allCompletedGoalsInfoDTO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return this.goalsService.getAllCompletedGoals(currentUserName);
    }

    @ModelAttribute("allUncompletedGoalsInfoDTO")
    public AllUncompletedGoalsInfoDTO allGoalsInfoDTO() {
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
    public List<ExpenseDTO> userExpenses() {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();
        return userByEmail.getExpenses();
    }

    @ModelAttribute("userFullNameDTO")
    public UserFullNameDTO userFullNameDTO() {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        return new UserFullNameDTO(
                userByEmail.getFirstName(),
                userByEmail.getLastName(),
                userByEmail.getEmail(),
                userByEmail.getMembershipType().toString(),
                userByEmail.getAccountNameAssignedForSubscription());
    }

    @ModelAttribute("expenseDTO")
    public ExpenseDTO expenseDTO() {
        return new ExpenseDTO();
    }


    @ModelAttribute("userAccounts")
    public List<AccountDTO> userAccounts() {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();
        return userByEmail.getAccounts();
    }

    @ModelAttribute("usersAccountCeil")
    public boolean accountCeilModel() {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();
        return userByEmail.getAccounts().size() < userByEmail.getUserAccountsAllowed();
    }

    @ModelAttribute("accountDTO")
    public AccountDTO accountDTO() {
        return new AccountDTO();
    }

    public UserExpensesDetailsDTO getUserByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserExpensesDetailsByEmail(currentUserName);

    }

    @ModelAttribute("changePlanDTO")
    public ChangeMembershipDTO changePlanDTO() {
        return new ChangeMembershipDTO();
    }


    @ModelAttribute("totalExpensesFunds")
    public String totalExpensesFunds() {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();
        String totalBalance = this.expenseService.getTotalValue(userByEmail.getExpenses());
        return String.valueOf(totalBalance);
    }

    @ModelAttribute("todaysDate")
    public String todaysDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String day = String.valueOf(LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US));
        return date.format(formatter) + " (" + day + ")";
    }

    @ModelAttribute("memberships")
    public MembershipType[] memberships() {
        return MembershipType.values();
    }
}
