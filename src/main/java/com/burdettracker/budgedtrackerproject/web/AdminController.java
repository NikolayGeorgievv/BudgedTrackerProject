package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.AllUsersInfoDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController extends BaseController{
    public AdminController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService, GoalsService goalsService, TransactionService transactionService) {
        super(expenses, userService, expenseService, accountService, goalsService, transactionService);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminPage")
    public String getAdminPage(){

        return "/adminPage";
    }


    @GetMapping("/filterUsers")
    public String filterUsersByEmail(@RequestParam("emailFilter") String email, Model model){
        AllUsersInfoDTO allUsersInfoDTO = this.userService.filterAllUsersByEmail(email);
        model.addAttribute("allUsersDTO", allUsersInfoDTO);
        return "adminPage";
    }

}
