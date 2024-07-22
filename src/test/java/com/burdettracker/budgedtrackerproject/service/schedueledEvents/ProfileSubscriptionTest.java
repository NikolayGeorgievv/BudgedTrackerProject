package com.burdettracker.budgedtrackerproject.service.schedueledEvents;

import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ProfileSubscriptionTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ProfileSubscription profileSubscription;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profileSubscription = new ProfileSubscription(userRepository, accountRepository);
    }

    @Test
    void payForProfileSubscriptionGold() {
        User user = new User();
        user.setRegisteredOnDate(LocalDate.of(2022, 1, LocalDate.now().getDayOfMonth()));
        user.setMembershipType(MembershipType.GOLD);
        Account account = new Account();
        account.setCurrentAmount(BigDecimal.valueOf(100));
        user.setAccountNameAssignedForSubscription("Test Account");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(accountRepository.getByName(user.getAccountNameAssignedForSubscription())).thenReturn(account);

        profileSubscription.payForProfileSubscription();

        verify(userRepository, times(1)).findAll();
        verify(accountRepository, times(1)).getByName(user.getAccountNameAssignedForSubscription());
        verify(accountRepository, times(1)).saveAndFlush(account);
    }

    @Test
    void payForProfileSubscriptionPremium() {
        User user = new User();
        user.setRegisteredOnDate(LocalDate.of(2022, 1, LocalDate.now().getDayOfMonth()));
        user.setMembershipType(MembershipType.PREMIUM);
        Account account = new Account();
        account.setCurrentAmount(BigDecimal.valueOf(100));
        user.setAccountNameAssignedForSubscription("Test Account");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(accountRepository.getByName(user.getAccountNameAssignedForSubscription())).thenReturn(account);

        profileSubscription.payForProfileSubscription();

        verify(userRepository, times(1)).findAll();
        verify(accountRepository, times(1)).getByName(user.getAccountNameAssignedForSubscription());
        verify(accountRepository, times(1)).saveAndFlush(account);
    }

    @Test
    void payForProfileSubscriptionThrowsException() {
        when(userRepository.findAll()).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> profileSubscription.payForProfileSubscription());
        verify(userRepository, times(1)).findAll();
    }

}