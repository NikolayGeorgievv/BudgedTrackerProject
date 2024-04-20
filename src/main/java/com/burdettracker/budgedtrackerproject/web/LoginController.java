package com.burdettracker.budgedtrackerproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("/users/login")
    public String login(){
        return "login";
    }

    @GetMapping("/users/login-error")
    public String loginerr(Model model){
        model.addAttribute("error", true);
        return "login";
    }

    @ModelAttribute
    @PostMapping("/users/login-error")
    public String fail(Model model){

        model.addAttribute("error", true);

        return "login";
    }
}
