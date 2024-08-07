package com.burdettracker.budgedtrackerproject.web.advisedControllers;

import com.burdettracker.budgedtrackerproject.model.dto.goal.EditGoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class GoalsController {

    private final GoalsService goalsService;
    private final UserService userService;

    public GoalsController(GoalsService goalsService, UserService userService) {
        this.goalsService = goalsService;
        this.userService = userService;
    }

    @GetMapping("/allGoalsPage")
    public String allGoalsPage() {

        return "/allGoalsPage";
    }


    @PostMapping("/editGoal")
    public String editGoal(@ModelAttribute("editGoalDTO") EditGoalDTO editGoalDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();


        goalsService.editGoal(editGoalDTO, currentUserName);
        return "redirect:/allGoalsPage";
    }

    @PostMapping("/addGoal")
    public String addGoal(@Valid GoalDTO goalDTO, BindingResult bindingResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        if (bindingResult.hasErrors()) {
            return "/allGoalsPage";
        }

        userService.addGoal(currentUserName, goalDTO);

        return "redirect:/allGoalsPage";
    }

    @DeleteMapping("/deleteGoal/{goalId}")
    public String deleteGoal(@PathVariable String goalId) {

        goalsService.deleteGoal(goalId);

        return "redirect:/allGoalsPage";
    }
}

