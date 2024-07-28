package com.burdettracker.budgedtrackerproject.web.advisedControllers;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.AccountTransactionsDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TransactionsController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final UserService userService;

    public TransactionsController(TransactionService transactionService, AccountService accountService, UserService userService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.userService = userService;
    }

    @ModelAttribute("isFreeAccount")
    public boolean isFreeAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserByEmail(currentUserName).getMembershipType().toString().equals("FREE");
    }


    @GetMapping("/accounts/{accountId}/transactions")
    public String getAccountTransactions(@PathVariable("accountId") String accountId, Model model) {

        AccountTransactionsDTO accountTransactionsDTO = transactionService.getAccountTransaction(accountId);
        AccountDTO currentAccountDTO = accountService.getAccountDTOById(accountId);
        model.addAttribute("accountTransactionsDTO", accountTransactionsDTO);
        model.addAttribute("currentAccountDTO", currentAccountDTO);
        return "singleAccountTransactionHistory";
    }

    @GetMapping("/getAllTransactions")
    public String getAllTransactions() {

        return "/allTransactionsPage";
    }
}
