package com.burdettracker.budgedtrackerproject.web;


import com.burdettracker.budgedtrackerproject.service.AWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class LogFileController {

    private final AWSService awsService;

    @Autowired
    public LogFileController(AWSService awsService) {
        this.awsService = awsService;
    }

    @GetMapping("/download-log")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> downloadLogFile() throws IOException {
        awsService.downloadLogFileFromS3();

        File file = new File(System.getenv("DOWNLOAD_DIRECTORY"));
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=myApp.log")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}