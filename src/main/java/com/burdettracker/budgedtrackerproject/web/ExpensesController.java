package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserFullNameDTO;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ExpensesController {

    private List<ExpenseDTO> expenses;
    private final UserService userService;
    private final ExpenseService expenseService;

    public ExpensesController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService) {
        this.expenses = expenses;
        this.userService = userService;
        this.expenseService = expenseService;
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
    @ModelAttribute("expenseDTO")
    public ExpenseDTO expenseDTO(){
        return new ExpenseDTO();
    }

    @GetMapping("/allExpensesPage")
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
            //TODO: ADD VALIDATION
           @Valid @ModelAttribute("expenseDTO") ExpenseDTO expenseDTO,
            BindingResult bindingResult){

        if (bindingResult.hasErrors()){

            return "allExpensesPageWithErrors";
        }

//        expenseService.addExpense(expenseDTO);

        return "allExpensesPage";
    }
    @GetMapping("/addExpense")
    public String getExpense(Model model){
        ExpenseDTO expenseDTO = new ExpenseDTO();
        model.addAttribute("expenseDTO",expenseDTO);
        return "allExpensesPage";
    }

    //TODO: total funds assigned to expenses, today's date,
}
