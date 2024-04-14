package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.user.UserFullNameDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.service.user.UserDetailImpl;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Random;

@Controller
public class MainPageController {

    private final UserService userService;
    private final UserDetailImpl userDetails;

    public MainPageController(UserService userService, UserDetailImpl userDetails) {
        this.userService = userService;
        this.userDetails = userDetails;
    }


    @ModelAttribute
    @GetMapping("/index")
    public String loggedIn(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User userByEmail = userService.getUserByEmail(currentUserName);
        UserFullNameDTO userFullNameDTO = new UserFullNameDTO(userByEmail.getFirstName(), userByEmail.getLastName());
        model.addAttribute("userFullNameDTO", userFullNameDTO);

        //TODO: Implement UserExpenseDTO

        List<Expense> expenses = userByEmail.getExpenses();
        model.addAttribute("userExpenses", expenses);

        int random = new Random().nextInt();
        model.addAttribute("randomNumber", random);

        return "index";
    }
}
