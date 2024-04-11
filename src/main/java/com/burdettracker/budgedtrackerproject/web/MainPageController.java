package com.burdettracker.budgedtrackerproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainPageController {

    @GetMapping("/index")
    public String loggedIn(){

        return "index";
    }
}
