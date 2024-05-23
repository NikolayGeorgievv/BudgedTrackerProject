package com.burdettracker.budgedtrackerproject.service.email;

import com.burdettracker.budgedtrackerproject.config.EmailVerificationConfig;
import com.burdettracker.budgedtrackerproject.model.dto.email.EmailVerificationDTO;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final EmailVerificationConfig emailVerificationConfig;
    private final RestTemplate restTemplate;
    private final Gson gson;

    public EmailVerificationServiceImpl(EmailVerificationConfig emailVerificationConfig, RestTemplate restTemplate, Gson gson) {
        this.emailVerificationConfig = emailVerificationConfig;
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    @Override
    public boolean isEmailValid(String email) {


        String validateEmailUrl = emailVerificationConfig.getSchema() +
                emailVerificationConfig.getHost()
                + email;

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", emailVerificationConfig.getApiKey());
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(validateEmailUrl, HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());

        EmailVerificationDTO emailVerificationDTO = gson.fromJson(response.getBody(), EmailVerificationDTO.class);
        System.out.println(emailVerificationDTO.isIs_role_account());
        return  emailVerificationDTO.isIs_role_account();
    }
}
