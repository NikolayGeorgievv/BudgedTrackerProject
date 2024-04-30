package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserFullNameDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.enums.CurrencyType;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
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
public class AccountsController {

    private final UserService userService;
    private final AccountService accountService;

    public AccountsController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @ModelAttribute("currencyTypes")
    public CurrencyType[] currencyTypes() {
        return CurrencyType.values();
    }

    @ModelAttribute("userFullNameDTO")
    public UserFullNameDTO userFullNameDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        UserExpensesDetailsDTO userByEmail = userService.getUserByEmail(currentUserName);

        return new UserFullNameDTO(userByEmail.getFirstName(), userByEmail.getLastName(), currentUserName);
    }


    @PostMapping("/addAccount")
    public String addAccount(
            @ModelAttribute("accountDTO") AccountDTO accountDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        this.userService.addAccount(currentUserName, accountDTO);

        return "redirect:/index";
    }

    @GetMapping("/allAccountsPage")
    public String allAccountPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        AllAccountsInfoDTO allAccountsInfoDTO = this.accountService.getAllAccounts(currentUserName);
        model.addAttribute("allAccountsInfoDTO", allAccountsInfoDTO);

        return "/allAccountsPage";
    }
}
