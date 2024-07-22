package com.burdettracker.budgedtrackerproject.service.schedueledEvents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LogsUpdaterTest {


    @Mock
    private S3Client s3Client;

    private LogsUpdater logsUpdater;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logsUpdater = new LogsUpdater();
        logsUpdater.setS3Client(s3Client);
    }

    @Test
    void uploadObject() {
        // Arrange
        String bucketName = "my-web-app-bucket-log";
        String key = "myApp.log";
        String filePath = "path/to/file";

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        PutObjectResponse putObjectResponse = PutObjectResponse.builder().build();

        when(s3Client.putObject(eq(putObjectRequest), eq(Paths.get(filePath)))).thenReturn(putObjectResponse);

        // Act
        logsUpdater.uploadObject(s3Client, bucketName, key, filePath);

        // Assert
        verify(s3Client, times(1)).putObject(eq(putObjectRequest), eq(Paths.get(filePath)));
    }
}
