package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.service.csvWriter.CSVService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CSVController {

    private final CSVService csvService;

    public CSVController(CSVService csvService) {
        this.csvService = csvService;
    }
    @GetMapping("/downloadCSV/{accountId}")
    public void downloadCSV(@PathVariable String accountId, HttpServletResponse response) throws IOException {
        csvService.generateCSV(response, accountId);
    }
}
