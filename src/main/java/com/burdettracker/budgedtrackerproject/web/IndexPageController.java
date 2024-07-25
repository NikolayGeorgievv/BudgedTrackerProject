package com.burdettracker.budgedtrackerproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPageController {

    @GetMapping("/index")
    public String homePage() {

        return "index";
    }

    @GetMapping("/")
    public String home() {

        return "index";
    }

    @GetMapping("FAQsPage")
    public String getFAQPage() {
        return "/FAQsPage";
    }

    @GetMapping("contactsPage")
    public String getContactPage() {
        return "/contactsPage";
    }

    @GetMapping("howItWorksPage")
    public String getHowItWorksPage() {
        return "/howItWorksPage";
    }
}
