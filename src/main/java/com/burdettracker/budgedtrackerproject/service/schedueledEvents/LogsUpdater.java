package com.burdettracker.budgedtrackerproject.service.schedueledEvents;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.file.Paths;

@Component
public class LogsUpdater {

    public void uploadObject(S3Client s3, String bucketName, String key, String filePath) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        PutObjectResponse response = s3.putObject(putObjectRequest, Paths.get(filePath));

        System.out.println("File uploaded successfully");
    }
//This rate is for testing purposes only.
//    @Scheduled(fixedRate = 20000)
    public void scheduledUpload() {
        String bucketName = "my-web-app-bucket-log";
        String key = "myApp.log";

        //todo: take the path in the properties file

        String localFilePath = "C:\\Users\\skull\\Downloads\\BudgedTrackerProject\\BudgedTrackerProject\\BudgedTrackerProject\\BudgedTrackerProject\\myApp.log";
        S3Client s3 = S3Client.builder().region(Region.EU_WEST_2).build();

        uploadObject(s3, bucketName, key, localFilePath);
    }
}
