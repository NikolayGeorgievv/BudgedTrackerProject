package com.burdettracker.budgedtrackerproject.service.user;

import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.burdettracker.budgedtrackerproject.utils.TestUtils.createDummyUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDetailImplTest {


    @Mock
    private UserRepository userRepository;

    private UserDetailImpl userDetail;

    @Autowired
    private RolesRepository rolesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetail = new UserDetailImpl(userRepository);
    }

    @Test
    void loadUserByUsername() {
        User user = createDummyUser(rolesRepository);
        userRepository.saveAndFlush(user);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails result = userDetail.loadUserByUsername(user.getEmail());

        assertEquals(result.getUsername(), user.getEmail());

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetail.loadUserByUsername(user.getEmail()));
    }
}