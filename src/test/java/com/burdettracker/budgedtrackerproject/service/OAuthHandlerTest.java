package com.burdettracker.budgedtrackerproject.service;

import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OAuthHandlerTest {
    private OAuthHandler oAuthHandler;
    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private OAuth2AuthenticationToken authentication;

    @BeforeEach
    public void setUp() {
        userService = Mockito.mock(UserService.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        authentication = Mockito.mock(OAuth2AuthenticationToken.class);
        oAuthHandler = new OAuthHandler(userService);
    }

    @Test
    public void testOnAuthenticationSuccess() throws Exception {
        OAuth2User oAuth2User = Mockito.mock(OAuth2User.class);
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute("email")).thenReturn("test@example.com");
        when(userService.login(any(String.class))).thenReturn(authentication);

        oAuthHandler.onAuthenticationSuccess(request, response, authentication);

        verify(userService).login(any(String.class));
    }
}