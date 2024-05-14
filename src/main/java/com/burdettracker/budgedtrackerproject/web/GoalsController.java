package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.AllGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.GoalDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GoalsController extends BaseController{

    private final GoalsService goalsService;
    private final UserService userService;
    private final Gson gson;
    public GoalsController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, AccountService accountService, GoalsService goalsService, UserService userService1, Gson gson) {
        super(expenses, userService, expenseService, accountService, goalsService);
        this.goalsService = goalsService;
        this.userService = userService1;
        this.gson = gson;
    }

//    @GetMapping("/allGoalsPage")
//    public String allGoalsPage(){
//
//        return "allGoalsPage";
//    }

//    @GetMapping("/allGoalsPage")
//    public ResponseEntity<String> responseEntity(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentUserName = authentication.getName();
//        AllGoalsInfoDTO allGoals = this.goalsService.getAllGoals(currentUserName);
//        Long length = goalLength(allGoals);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(length));
//        return ResponseEntity.ok().headers(headers).body("allGoalsPage");
//    }

    @PostMapping("/addGoal")
    public String addGoal(@ModelAttribute("goalDTO")GoalDTO goalDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();


        userService.addGoal(currentUserName,goalDTO);

        return "redirect:/allGoalsPage";
    }

    public Long goalLength(AllGoalsInfoDTO allGoalsInfoDTO){
        String json = gson.toJson(allGoalsInfoDTO.getAllGoals());
        return (long) json.length();
    }
}

