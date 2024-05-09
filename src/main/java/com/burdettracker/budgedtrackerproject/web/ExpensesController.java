package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.EditExpenseInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserFullNameDTO;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ExpensesController {

    private List<ExpenseDTO> expenses;
    private final UserService userService;
    private final ExpenseService expenseService;
    private final AccountService accountService;

    public ExpensesController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService) {
        this.expenses = expenses;
        this.userService = userService;
        this.expenseService = expenseService;
        this.accountService = accountService;
    }
    @ModelAttribute("allAccountsInfoDTO")
    public AllAccountsInfoDTO allAccountsInfoDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return this.accountService.getAllAccounts(currentUserName);
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
    public String allBillsPage(Model model){
        double totalBalance = expenses.stream().mapToDouble(e -> Double.parseDouble(String.valueOf(e.getAssigned()))).sum();
        model.addAttribute("totalExpensesFunds", totalBalance);

        model.addAttribute("todaysDate", todaysDate());
        return "allExpensesPage";
    }

    @PostMapping("/addExpense")
    public String addExpense(
           @Valid @ModelAttribute("expenseDTO") ExpenseDTO expenseDTO,BindingResult bindingResult){

        if (bindingResult.hasErrors()){

            return "allExpensesPageWithErrors";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        try {
            userService.addExpense(currentUserName,expenseDTO);
        }catch (RuntimeException e){
            bindingResult.addError(new FieldError("expenseDTO","periodDate","Please choose a correct date."));
            return "allExpensesPageWithErrors";
        }



        return "redirect:/allExpensesPage";
    }
    @GetMapping("/addExpense")
    public String getExpense(Model model){
        ExpenseDTO expenseDTO = new ExpenseDTO();
        model.addAttribute("expenseDTO",expenseDTO);
        return "allExpensesPageWithErrors";
    }

    //TODO: total funds assigned to expenses, today's date,


    @GetMapping("/deleteExpense/{expenseId}")
    public String deleteExpense (@PathVariable String expenseId){

        expenseService.deleteById(expenseId);

        return "redirect:/allExpensesPage";
    }

    @PostMapping("/editExpense")
    public String editAccount(
            @ModelAttribute("editExpenseInfoDTO") EditExpenseInfoDTO editExpenseInfoDTO, BindingResult bindingResult){


        //TODO: FIX VALIDATION FOR INCORRECT DATE
        try {
            expenseService.editExpense(editExpenseInfoDTO);
        }catch (RuntimeException e){
            bindingResult.addError(new FieldError("expenseDTO","periodDate1","Please choose a correct date."));
            return "/allExpensesPage";
        }


        return "redirect:/allExpensesPage";
    }


    public UserExpensesDetailsDTO getUserByEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserByEmail(currentUserName);

    }

    public String todaysDate(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return date.format(formatter);
    }
}
