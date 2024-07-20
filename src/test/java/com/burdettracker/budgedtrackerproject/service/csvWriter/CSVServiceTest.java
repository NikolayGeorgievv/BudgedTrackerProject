package com.burdettracker.budgedtrackerproject.service.csvWriter;

import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Transaction;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CSVServiceTest {


    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;


    @InjectMocks
    private CSVService csvService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateCSVWritesCorrectData() throws IOException {
        String accountId = "1";
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(BigDecimal.valueOf(100));
        transaction1.setCompletedOn(LocalDate.now());

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(BigDecimal.valueOf(200));
        transaction2.setCompletedOn(LocalDate.now());

        Account account = new Account();
        account.setExpenseTransactionHistory(Arrays.asList(transaction1, transaction2));
        when(accountRepository.getReferenceById(anyLong())).thenReturn(account);

        MockHttpServletResponse response = new MockHttpServletResponse();
        csvService.generateCSV(response, accountId);

        verify(accountRepository).getReferenceById(Long.parseLong(accountId));
        assertEquals("text/csv", response.getContentType());
    }

    @Test
    void generateAllCSVWritesCorrectData() throws IOException {
        String email = "test@test.com";
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(BigDecimal.valueOf(100));
        transaction1.setCompletedOn(LocalDate.now());

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(BigDecimal.valueOf(200));
        transaction2.setCompletedOn(LocalDate.now());

        User user = new User();
        user.setTransactions(Arrays.asList(transaction1, transaction2));
        when(userService.getUserByEmail(anyString())).thenReturn(user);

        MockHttpServletResponse response = new MockHttpServletResponse();
        csvService.generateAllCSV(response, email);

        verify(userService).getUserByEmail(email);
        assertEquals("text/csv", response.getContentType());
    }
}