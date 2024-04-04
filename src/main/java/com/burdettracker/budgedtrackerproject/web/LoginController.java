package com.burdettracker.budgedtrackerproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("login.html")
    public String login(){

        return "login";
    }

    @PostMapping("/users/login")
    public String loggedIn(){

        return "index";
    }
}
