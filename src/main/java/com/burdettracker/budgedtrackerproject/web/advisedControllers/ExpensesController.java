package com.burdettracker.budgedtrackerproject.web.advisedControllers;

import com.burdettracker.budgedtrackerproject.model.dto.expense.EditExpenseInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ExpensesController {

    private final UserService userService;
    private final ExpenseService expenseService;

    public ExpensesController(UserService userService, ExpenseService expenseService) {
        this.userService = userService;
        this.expenseService = expenseService;
    }

    @GetMapping("/allExpensesPage")
    public String allBillsPage(Model model) {
        return "allExpensesPage";
    }

    @PostMapping("/addExpense")
    public String addExpense(
            @Valid @ModelAttribute("expenseDTO") ExpenseDTO expenseDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/allExpensesPage";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        try {
            userService.addExpense(currentUserName, expenseDTO);
        } catch (RuntimeException e) {
            bindingResult.addError(new FieldError("expenseDTO", "periodDate", "Please choose a correct date."));

            return "/allExpensesPage";
        }


        return "redirect:/allExpensesPage";
    }


    @DeleteMapping("/deleteExpense/{expenseId}")
    public String deleteExpense(@PathVariable String expenseId) {

        expenseService.deleteById(expenseId);

        return "redirect:/allExpensesPage";
    }

    @PostMapping("/editExpense")
    public String editExpense(
            @ModelAttribute("editExpenseInfoDTO") EditExpenseInfoDTO editExpenseInfoDTO) {

        expenseService.editExpense(editExpenseInfoDTO);

        return "redirect:/allExpensesPage";
    }

    @GetMapping("/categorySort")
    public String sortByCategory(String category, Model model) {

        List<ExpenseDTO> sortedExpenses = expenseService.sortByCategory(category);
        String categoryTotalBalance = this.expenseService.getTotalValue(sortedExpenses);

        model.addAttribute("categoryTotalBalance", categoryTotalBalance);
        model.addAttribute("category", category);
        model.addAttribute("expensesByCategory", sortedExpenses);

        return "/categorizedExpenses";
    }

}
