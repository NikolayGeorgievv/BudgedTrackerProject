package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GoalsController extends BaseController{

    public GoalsController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService, AccountService accountService1) {
        super(expenses, userService, expenseService, accountService, accountService1);
    }

    @GetMapping("allGoalsPage")
    public String allGoalsPage(){

        return "allGoalsPage";
    }


}

