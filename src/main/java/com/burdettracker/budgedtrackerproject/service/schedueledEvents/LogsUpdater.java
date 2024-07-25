package com.burdettracker.budgedtrackerproject.service.schedueledEvents;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.file.Paths;

@Component
public class LogsUpdater {

    @Value("${my.app.log.path}")
    String localFilePath;
    @Value("${bucket.name}")
    String bucketName;
    @Value("${key.name}")
    String key;

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

    //Logs are updated every hour
    @Scheduled(cron = "0 0 * * * *")
    public void scheduledUpload() {
        S3Client s3 = S3Client.builder().region(Region.EU_WEST_2).build();

        uploadObject(s3, bucketName, key, localFilePath);
    }

    public void setS3Client(S3Client s3Client) {
    }
}
