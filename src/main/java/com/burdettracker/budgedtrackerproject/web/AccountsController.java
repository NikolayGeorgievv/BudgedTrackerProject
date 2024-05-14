package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AccountsController extends BaseController{
    private final UserService userService;
    private final AccountService accountService;

    public AccountsController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService, UserService userService1, AccountService accountService2, List<AccountDTO> accounts, List<ExpenseDTO> expenses1, GoalsService goalsService) {
        super(expenses, userService, expenseService, accountService, goalsService);
        this.userService = userService1;
        this.accountService = accountService2;
    }




    @PostMapping("/editAccount")
    public String editAccount(
            @ModelAttribute("editAccountInfoDTO") EditAccountInfoDTO editAccountInfoDTO,
            @ModelAttribute("allAccountsInfoDTO") AllAccountsInfoDTO allAccountsInfoDTO){

        accountService.updateAccountById(editAccountInfoDTO);

        return "redirect:/allAccountsPage";
    }
    @GetMapping("/deleteAccount/{accountId}")
    public String deleteAccount (@PathVariable String accountId){

        accountService.deleteAccountById(accountId);

        return "redirect:/allAccountsPage";
    }

    @PostMapping("/addAccount")
    public String addAccount(
            @ModelAttribute("accountDTO") AccountDTO accountDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        this.userService.addAccount(currentUserName, accountDTO);

        return "redirect:/allAccountsPage";
    }


    @GetMapping("/allAccountsPage")
    public String allAccountPage() {

        return "/allAccountsPage";
    }
}
