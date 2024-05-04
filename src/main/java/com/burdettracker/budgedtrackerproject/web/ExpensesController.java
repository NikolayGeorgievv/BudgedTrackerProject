package com.burdettracker.budgedtrackerproject.web;

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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ExpensesController {

    private List<ExpenseDTO> expenses;
    private final UserService userService;

    public ExpensesController(List<ExpenseDTO> expenses, UserService userService) {
        this.expenses = expenses;
        this.userService = userService;
    }

    @ModelAttribute("userExpenses")
    public Model userExpenses(Model model){
        UserExpensesDetailsDTO userByEmail = getUserByEmail();
        expenses = userByEmail.getExpenses();
        model.addAttribute("userExpenses", expenses);
        return model;
    }
    @ModelAttribute("userFullNameDTO")
    public UserFullNameDTO userFullNameDTO(){
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        return new UserFullNameDTO(userByEmail.getFirstName(), userByEmail.getLastName(), userByEmail.getEmail());
    }

    @GetMapping("/allBillsPage")
    public String allBillsPage(){



        return "allExpensesPage";
    }

    public UserExpensesDetailsDTO getUserByEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserByEmail(currentUserName);

    }

    @PostMapping("/addExpense")
    public String addExpense(
            @ModelAttribute("expenseDTO") ExpenseDTO expenseDTO){
        return "allExpensesPage";
    }

    //TODO: total funds assigned to expenses, today's date,
}
