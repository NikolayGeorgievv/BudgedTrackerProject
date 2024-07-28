package com.burdettracker.budgedtrackerproject.web.advisedControllers;

import com.burdettracker.budgedtrackerproject.model.dto.membership.ChangeMembershipDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainPageController {

    private final UserService userService;

    public MainPageController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    @GetMapping("/homePage")
    public String loggedIn(Model model) {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        if (userByEmail.getAccounts().size() != userByEmail.getUserAccountsAllowed()) {
            model.addAttribute("usersAccountCeil", true);
        }

        return "redirect:/homePage";
    }

    @PostMapping("/changePlan")
    public String changeMembershipPlan(@ModelAttribute("changePlanDTO") @Valid ChangeMembershipDTO changePlanDTO, BindingResult bindingResult) {

        try {
            userService.changeUserPlan(changePlanDTO, getUserByEmail().getEmail());
        } catch (RuntimeException er) {

            bindingResult.addError(new FieldError("changePlanDTO", "membership", "Can't downgrade.Number of allowed accounts exceeded."));
            return "/homePage";
        }
        return "redirect:/homePage";
    }

    public UserExpensesDetailsDTO getUserByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserExpensesDetailsByEmail(currentUserName);

    }


}
