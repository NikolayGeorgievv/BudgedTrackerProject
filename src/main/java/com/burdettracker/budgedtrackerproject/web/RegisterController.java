package com.burdettracker.budgedtrackerproject.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @PostMapping("/users/register")
    public String registerUser(){

        return "login";
    }

    @GetMapping("registerForm.html")
    public String register(){

        return "registerForm";
    }
}
