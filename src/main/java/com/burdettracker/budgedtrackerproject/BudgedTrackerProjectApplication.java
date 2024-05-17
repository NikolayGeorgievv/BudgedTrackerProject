package com.burdettracker.budgedtrackerproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class BudgedTrackerProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgedTrackerProjectApplication.class, args);
    }

}
