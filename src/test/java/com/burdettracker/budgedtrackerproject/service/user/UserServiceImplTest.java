package com.burdettracker.budgedtrackerproject.service.user;

import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailImpl userDetail;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, null, null, userDetail, null, null, null, null, null, null, null);
    }

    @Test
    void login() {
        String email = "test@test.com";
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetail.loadUserByUsername(email)).thenReturn(userDetails);

        Authentication auth = userService.login(email);

        Mockito.verify(userDetail, times(1)).loadUserByUsername(email);
        assertEquals(auth.getPrincipal(), userDetails);
    }

    @Test
    void getUserByEmail() {
        String email = "test@test.com";
        User user = new User();
        when(userRepository.getByEmail(email)).thenReturn(user);

        User result = userService.getUserByEmail(email);

        Mockito.verify(userRepository, times(1)).getByEmail(email);
        assertEquals(result, user);
    }

    @Test
    void saveAndFlush() {
        User user = new User();
        userService.saveAndFlush(user);

        Mockito.verify(userRepository, times(1)).saveAndFlush(user);
    }

}