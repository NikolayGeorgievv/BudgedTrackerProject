package com.burdettracker.budgedtrackerproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BudgedTrackerProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgedTrackerProjectApplication.class, args);
    }

}
