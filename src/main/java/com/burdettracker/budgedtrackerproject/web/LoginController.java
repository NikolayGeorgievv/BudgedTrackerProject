package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.user.LoginUserDTO;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login.html")
    public String login(LoginUserDTO loginUserDTO){

        if (userService.login(loginUserDTO)) {

            return "index";
        }

        return "login";
    }

    @PostMapping("/users/login")
    public String loggedIn(){

        return "index";
    }
}
