package com.burdettracker.budgedtrackerproject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class AWSService {

    @Value("${bucket.name}")
    String bucketName;
    @Value("${key.name}")
    String key;




    public void downloadLogFileFromS3() {

        Region region = Region.EU_WEST_2;
        S3Client s3 = S3Client.builder().region(region).build();

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            Path localFilePath = Paths.get(System.getenv("DOWNLOAD_DIRECTORY"));

            // Check if file already exists, if so, delete it
            if (Files.exists(localFilePath)) {
                Files.delete(localFilePath);
            }

            s3.getObject(getObjectRequest, ResponseTransformer.toFile(localFilePath));

        } catch (S3Exception | IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed to download file from S3", e);
        }
    }
}
