package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.AccountTransactionsDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.category.CategoryService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TransactionsController extends BaseController{
    public TransactionsController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, CategoryService categoryService, AccountService accountService, GoalsService goalsService, TransactionService transactionService) {
        super(expenses, userService, expenseService, accountService, goalsService, transactionService, categoryService);
    }


    @GetMapping("/accounts/{accountId}/transactions")
    public String getAccountTransactions(@PathVariable("accountId") String accountId, Model model){

       AccountTransactionsDTO accountTransactionsDTO =  transactionService.getAccountTransaction(accountId);
        AccountDTO currentAccountDTO = accountService.getAccountDTOById(accountId);
        model.addAttribute("accountTransactionsDTO", accountTransactionsDTO);
        model.addAttribute("currentAccountDTO", currentAccountDTO);
        return "singleAccountTransactionHistory";
    }

    @GetMapping("/getAllTransactions")
    public String getAllTransactions(){

        return "/allTransactionsPage";
    }
}
