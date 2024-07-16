package com.burdettracker.budgedtrackerproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.nio.file.Path;

@Component
public class AWSTest implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String bucketName = "my-web-app-bucket-log"; // replace with your bucket name
        String key = "myApp.log"; // replace with your object key

        Region region = Region.EU_WEST_2; // replace with your bucket region
        S3Client s3 = S3Client.builder().region(region).build();

        getObject(s3, bucketName, key);



    }
    public static void getObject(S3Client s3, String bucketName, String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            Path localFilePath = Path.of("C:\\Users\\skull\\Downloads\\myApp.log"); // replace with the path where you want to save the file

            s3.getObject(getObjectRequest, localFilePath);

            System.out.println("File downloaded successfully");
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
