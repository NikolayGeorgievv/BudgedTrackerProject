package com.burdettracker.budgedtrackerproject.service.email;

import com.burdettracker.budgedtrackerproject.config.EmailVerificationConfig;
import com.burdettracker.budgedtrackerproject.model.dto.email.EmailVerificationDTO;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EmailVerificationServiceImplTest {

    @Mock
    private EmailVerificationConfig emailVerificationConfig;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Gson gson;

    private EmailVerificationServiceImpl emailVerificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailVerificationService = new EmailVerificationServiceImpl(emailVerificationConfig, restTemplate, gson);
    }

    @Test
    void isEmailValid() {
        String email = "test@test.com";
        String validateEmailUrl = "http://api.quickemailverification.com/" + email;
        String apiKey = "123456";
        EmailVerificationDTO emailVerificationDTO = new EmailVerificationDTO();
        emailVerificationDTO.setIs_role_account(true);

        when(emailVerificationConfig.getSchema()).thenReturn("http://api.quickemailverification.com/");
        when(emailVerificationConfig.getHost()).thenReturn(email);
        when(emailVerificationConfig.getApiKey()).thenReturn(apiKey);
        when(gson.fromJson(anyString(), eq(EmailVerificationDTO.class))).thenReturn(emailVerificationDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"is_role_account\": true}", HttpStatus.OK);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);

        // Act
        boolean result = emailVerificationService.isEmailValid(email);

        // Assert
        assertTrue(result);
        verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class));
        verify(gson, times(1)).fromJson(anyString(), eq(EmailVerificationDTO.class));
    }
}