package com.burdettracker.budgedtrackerproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/homePage")
    public String homePage(){

        return "homePage";
    }

    @GetMapping("/")
    public String home(){

        return "homePage";
    }

    @GetMapping("FAQsPage")
    public String getFAQPage(){
        return "/FAQsPage";
    }
}
