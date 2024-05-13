package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.EditExpenseInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ExpensesController extends BaseController{

    private List<ExpenseDTO> expenses;
    private final UserService userService;
    private final ExpenseService expenseService;
    private final AccountService accountService;
    private List<AccountDTO> accounts;

    public ExpensesController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService, AccountService accountService1, List<ExpenseDTO> expenses1, UserService userService1, ExpenseService expenseService1, AccountService accountService2, List<AccountDTO> accounts) {
        super(expenses, userService, expenseService, accountService, accountService1);
        this.expenses = expenses1;
        this.userService = userService1;
        this.expenseService = expenseService1;
        this.accountService = accountService2;
        this.accounts = accounts;
    }



    @GetMapping("/allExpensesPage")
    public String allBillsPage(Model model) {
        return "allExpensesPage";
    }

    @PostMapping("/addExpense")
    public String addExpense(
            @Valid @ModelAttribute("expenseDTO") ExpenseDTO expenseDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return "allExpensesPageWithErrors";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        try {
            userService.addExpense(currentUserName, expenseDTO);
        } catch (RuntimeException e) {
            bindingResult.addError(new FieldError("expenseDTO", "periodDate", "Please choose a correct date."));
            return "allExpensesPageWithErrors";
        }


        return "redirect:/allExpensesPage";
    }

    @GetMapping("/addExpense")
    public String getExpense(Model model) {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        model.addAttribute("expenseDTO", expenseDTO);
        return "allExpensesPageWithErrors";
    }


    @GetMapping("/deleteExpense/{expenseId}")
    public String deleteExpense(@PathVariable String expenseId) {

        expenseService.deleteById(expenseId);

        return "redirect:/allExpensesPage";
    }

    @PostMapping("/editExpense")
    public String editAccount(
            @ModelAttribute("editExpenseInfoDTO") EditExpenseInfoDTO editExpenseInfoDTO) {

        expenseService.editExpense(editExpenseInfoDTO);

        return "redirect:/allExpensesPage";
    }
}
