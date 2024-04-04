package com.burdettracker.budgedtrackerproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/homePage.html")
    public String homePage(){

        return "homePage";
    }

    @GetMapping("/")
    public String home(){

        return "homePage";
    }

}
