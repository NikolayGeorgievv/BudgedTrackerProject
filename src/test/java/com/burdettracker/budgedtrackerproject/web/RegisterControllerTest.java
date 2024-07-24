package com.burdettracker.budgedtrackerproject.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void register() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/register")
                                .param("firstName", "test")
                                .param("lastName", "test")
                                .param("email", "myemail@testemail.com")
                                .param("password", "test")
                                .param("confirmPassword", "test")
                                .param("phoneNumber", "testNumber")
                                .param("membership", "GOLD")
                                .param("termsAccepted", "true")
                                .with(csrf())
                ).andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login"));
    }

    @Test
    void register_passwordsNotMatch() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/register")
                                .param("firstName", "test")
                                .param("lastName", "test")
                                .param("email", "myemail@testemail.com")
                                .param("password", "test")
                                .param("confirmPassword", "test1")
                                .param("phoneNumber", "testNumber")
                                .param("membership", "GOLD")
                                .param("termsAccepted", "true")
                                .with(csrf())
                ).andExpect(status().isOk())
                .andExpect(view().name("registerForm"));
    }

    @Test
    void register_invalidEmail() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/register")
                                .param("firstName", "test")
                                .param("lastName", "test")
                                .param("email", "invalidEmail")
                                .param("password", "test")
                                .param("confirmPassword", "test")
                                .param("phoneNumber", "testNumber")
                                .param("membership", "GOLD")
                                .param("termsAccepted", "true")
                                .with(csrf())
                ).andExpect(status().isOk())
                .andExpect(view().name("registerForm"));
    }

    @Test
    void register_missingParameters() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/register")
                                .param("firstName", "test")
                                .param("lastName", "test")
                                .param("email", "myemail@testemail.com")
                                .param("password", "test")
                                .param("confirmPassword", "test")
                                .param("phoneNumber", "testNumber")
                                .param("membership", "GOLD")
                                .with(csrf())
                ).andExpect(status().isOk())
                .andExpect(view().name("registerForm"));
    }

    @Test
    void register_get() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerForm"));
    }

    @Test
    void termsAndConditions() throws Exception {
        mockMvc.perform(get("/users/termsAndConditions.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("termsAndConditions.html"));
    }
}