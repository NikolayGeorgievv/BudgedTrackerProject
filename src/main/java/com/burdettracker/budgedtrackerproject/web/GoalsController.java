package com.burdettracker.budgedtrackerproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoalsController {

    @GetMapping("allGoalsPage")
    public String allGoalsPage(){

        return "allGoalsPage";
    }
}
