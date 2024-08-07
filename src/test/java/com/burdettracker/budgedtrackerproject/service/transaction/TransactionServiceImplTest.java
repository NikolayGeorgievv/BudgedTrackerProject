package com.burdettracker.budgedtrackerproject.service.transaction;

import com.burdettracker.budgedtrackerproject.model.dto.transaction.AccountTransactionsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.TransactionInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.*;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.TransactionRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {


    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addExpenseTransaction() {
        Expense expense = new Expense();
        Category category = new Category("test", false);
        Account account = new Account();
        account.setName("test");
        expense.setCategory(category);
        expense.setAccount(account);
        User user = new User();

        transactionService.addExpenseTransaction(expense, user);

        verify(transactionRepository, times(1)).saveAndFlush(any());
        verify(userRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void getAccountTransaction() {
        String accountId = "1";
        Account account = new Account();
        TransactionInfoDTO transaction1 = new TransactionInfoDTO();
        TransactionInfoDTO transaction2 = new TransactionInfoDTO();

        when(accountRepository.getReferenceById(Long.valueOf(accountId))).thenReturn(account);
        when(modelMapper.map(any(), any())).thenReturn(new TransactionInfoDTO[]{transaction1, transaction2});



        AccountTransactionsDTO result = transactionService.getAccountTransaction(accountId);

        verify(accountRepository, times(1)).getReferenceById(Long.valueOf(accountId));
        assertEquals(2, result.getAllTransactions().size());
    }

    @Test
    void getAllTransactionsInfo() {
        String email = "test@test.com";
        User user = new User();
        user.setEmail(email);
        TransactionInfoDTO transaction1 = new TransactionInfoDTO();
        TransactionInfoDTO transaction2 = new TransactionInfoDTO();

        when(userRepository.getByEmail(email)).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(new TransactionInfoDTO[]{transaction1, transaction2});


        transactionService.getAllTransactionsInfo(email);
        verify(userRepository, times(1)).getByEmail(anyString());
    }

    @Test
    void createGoalTransaction() {
        Goal goal = new Goal();
        Account account = new Account();
        account.setName("testAcc");

        goal.setName("goalName");
        goal.setCurrentAmount(BigDecimal.valueOf(100));
        goal.setCreatedOn(LocalDate.of(2021, 8, 10));
        goal.setAccount(account);

        Transaction resultTransaction = transactionService.createGoalTransaction(goal);

        assertEquals(goal.getName(), resultTransaction.getPaidTo());
        assertEquals(goal.getCurrentAmount(), resultTransaction.getAmount());
        assertEquals(goal.getAccount().getName(), resultTransaction.getAccount().getName());
    }

    @Test
    void saveAndFlush() {
        Transaction transaction = new Transaction();

        transactionService.saveAndFlush(transaction);

        verify(transactionRepository, times(1)).saveAndFlush(transaction);
    }

    @Test
    void saveAllAndFlush() {
        List<Transaction> transactions = Arrays.asList(new Transaction(), new Transaction());

        transactionService.saveAllAndFlush(transactions);

        verify(transactionRepository, times(1)).saveAllAndFlush(transactions);
    }

}