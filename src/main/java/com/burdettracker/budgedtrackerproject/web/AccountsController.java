package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserFullNameDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @ModelAttribute("allAccountsInfoDTO")
    public AllAccountsInfoDTO allAccountsInfoDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return this.accountService.getAllAccounts(currentUserName);
    }

    @ModelAttribute("userFullNameDTO")
    public UserFullNameDTO userFullNameDTO() {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        return new UserFullNameDTO(userByEmail.getFirstName(), userByEmail.getLastName(),userByEmail.getEmail());
    }
    @ModelAttribute("usersAccountCeil")
    public Model accountCeilModel(Model model){
        UserExpensesDetailsDTO userByEmail = getUserByEmail();
        if (userByEmail.getAccounts().size() != userByEmail.getUserAccountsAllowed()){
            model.addAttribute("usersAccountCeil", true);
        }
        return model;
    }
    @ModelAttribute("expenseDTO")
    public ExpenseDTO expenseDTO(){
        return new ExpenseDTO();
    }

    @ModelAttribute("userAccounts")
    public Model userAccountsModel(Model model){
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        accounts = userByEmail.getAccounts();
        model.addAttribute("userAccounts", accounts);
        return model;
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

    //TODO: ADD TH:EACH ALL ACC ON SIDEBAR

    @GetMapping("/allAccountsPage")
    public String allAccountPage() {

        return "/allAccountsPage";
    }

    public UserExpensesDetailsDTO getUserByEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserByEmail(currentUserName);

    }
}
