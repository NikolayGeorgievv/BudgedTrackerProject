package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.service.csvWriter.CSVService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CSVRestController {

    private final CSVService csvService;

    public CSVRestController(CSVService csvService) {
        this.csvService = csvService;
    }

    @GetMapping("/downloadCSV/{accountId}")
    public void downloadCSV(@PathVariable String accountId, HttpServletResponse response) throws IOException {
        csvService.generateCSV(response, accountId);
    }

    @GetMapping("/downloadAllTransactionsCSV")
    public void downLoadAllTransactionsCSV(HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        csvService.generateAllCSV(response, currentUserName);
    }
}
