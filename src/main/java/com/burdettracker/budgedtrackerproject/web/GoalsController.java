package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.EditGoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
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
public class GoalsController extends BaseController{

    public GoalsController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService, GoalsService goalsService, TransactionService transactionService) {
        super(expenses, userService, expenseService, accountService, goalsService, transactionService);

    }

    @GetMapping("/allGoalsPage")
    public String allGoalsPage(){

        return "allGoalsPage";
    }


    @PostMapping("/editGoal")
    public String editGoal(@ModelAttribute("editGoalDTO") EditGoalDTO editGoalDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();


        goalsService.editGoal(editGoalDTO, currentUserName);
        return "redirect:/allGoalsPage";
    }

    @PostMapping("/addGoal")
    public String addGoal(@ModelAttribute("goalDTO")GoalDTO goalDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();


        userService.addGoal(currentUserName,goalDTO);

        return "redirect:/allGoalsPage";
    }

    @GetMapping("/deleteGoal/{goalId}")
    public String deleteGoal (@PathVariable String goalId){

        goalsService.deleteGoal(goalId);

        return "redirect:/allGoalsPage";
    }
}

