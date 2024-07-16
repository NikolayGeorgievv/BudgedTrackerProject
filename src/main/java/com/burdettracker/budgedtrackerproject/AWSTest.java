package com.burdettracker.budgedtrackerproject;

import org.springframework.boot.CommandLineRunner;
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
import java.nio.file.StandardOpenOption;

@Component
public class AWSTest implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String bucketName = "my-web-app-bucket-log";
        String key = "myApp.log";

        Region region = Region.EU_WEST_2;
        S3Client s3 = S3Client.builder().region(region).build();

        getObject(s3, bucketName, key);



    }
    public static void getObject(S3Client s3, String bucketName, String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            Path localFilePath = Paths.get("C:\\Users\\skull\\Downloads\\myApp.log");

            // Delete the file if it exists
            try {
                Files.deleteIfExists(localFilePath);
            } catch (IOException e) {
                System.err.println("Unable to delete file: " + e.getMessage());
                return;
            }

            // Download the file
            s3.getObject(getObjectRequest, ResponseTransformer.toFile(localFilePath));

            System.out.println("File downloaded successfully");
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
