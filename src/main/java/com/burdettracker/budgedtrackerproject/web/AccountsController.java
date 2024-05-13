package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
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
public class AccountsController {

    private final UserService userService;
    private final AccountService accountService;
    private List<AccountDTO> accounts;

    public AccountsController(UserService userService, AccountService accountService, List<AccountDTO> accounts) {
        this.userService = userService;
        this.accountService = accountService;
        this.accounts = accounts;
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
