package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainPageController {

    private final UserService userService;

    public MainPageController(UserService userService) {
        this.userService = userService;

    }
    @ModelAttribute
    @GetMapping("/index")
    public String loggedIn(Model model) {
        UserExpensesDetailsDTO userByEmail = getUserByEmail();

        if (userByEmail.getAccounts().size() != userByEmail.getUserAccountsAllowed()){
            model.addAttribute("usersAccountCeil", true);
        }

        return "redirect:/index";
    }

    public UserExpensesDetailsDTO getUserByEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return userService.getUserByEmail(currentUserName);

    }
}
