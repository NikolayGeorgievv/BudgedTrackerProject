package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.category.CategoryService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AccountsController extends BaseController{


    public AccountsController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService, TransactionService transactionService, GoalsService goalsService, CategoryService categoryService) {
        super(expenses, userService, expenseService, accountService, goalsService, transactionService, categoryService);

    }

    @PostMapping("/editAccount")
    public String editAccount(
            @ModelAttribute("editAccountInfoDTO") EditAccountInfoDTO editAccountInfoDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        accountService.updateAccountById(editAccountInfoDTO, currentUserName);

        return "redirect:/allAccountsPage";
    }
    @DeleteMapping("/deleteAccount/{accountId}")
    public String deleteAccount (@PathVariable String accountId){

        accountService.deleteAccountById(accountId);

        return "redirect:/allAccountsPage";
    }

    @PostMapping("/addAccount")
    public String addAccount(
            @ModelAttribute("accountDTO") @Valid AccountDTO accountDTO,  BindingResult bindingResult) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        if (bindingResult.hasErrors()) {
            return "/allAccountsPage";
        }

        this.userService.addAccount(currentUserName, accountDTO);

        return "redirect:/allAccountsPage";
    }


    @GetMapping("/allAccountsPage")
    public String allAccountPage() {

        return "/allAccountsPage";
    }
}
