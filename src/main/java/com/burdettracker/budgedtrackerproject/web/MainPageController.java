package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.membership.ChangeMembershipDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
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
public class MainPageController extends BaseController{



    public MainPageController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService, TransactionService transactionService, GoalsService goalsService) {
        super(expenses, userService, expenseService, accountService, goalsService, transactionService);
    }

    @ModelAttribute
    @GetMapping("/homePage")
    public String loggedIn(Model model) {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        if (userByEmail.getAccounts().size() != userByEmail.getUserAccountsAllowed()){
            model.addAttribute("usersAccountCeil", true);
        }

        return "redirect:/homePage";
    }

    @PostMapping("/changePlan")
    public String changeMembershipPlan(@ModelAttribute("changePlanDTO")ChangeMembershipDTO changePlanDTO){

        userService.changeUserPlan(changePlanDTO, getUserByEmail().getEmail());

        return "redirect:/homePage";
    }

    public UserExpensesDetailsDTO getUserByEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserExpensesDetailsByEmail(currentUserName);

    }



}
