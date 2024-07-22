package com.burdettracker.budgedtrackerproject.service.schedueledEvents;

import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseUpdaterTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private ExpenseService expenseService;

    private ExpenseUpdater expenseUpdater;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        expenseUpdater = new ExpenseUpdater(transactionService, accountService, expenseService);
    }

    @Test
    void updateExpenseWeekly() {
        Expense expense = new Expense();
        expense.setDateDue(LocalDate.now());
        expense.setPeriod("weekly");
        expense.setAssigned(BigDecimal.TEN);
        Account account = new Account();
        account.setCurrentAmount(BigDecimal.valueOf(100));
        expense.setAccount(account);

        when(expenseService.findAllByDateDue(LocalDate.now())).thenReturn(Collections.singletonList(expense));

        expenseUpdater.updateExpense();

        verify(transactionService, times(1)).addExpenseTransaction(expense, expense.getUser());
        verify(accountService, times(1)).saveAndFlush(account);
        verify(expenseService, times(1)).saveAndFlush(expense);
    }

    @Test
    void updateExpenseLastDayOfMonth() {
        // Arrange
        LocalDate todaysDate = LocalDate.now();
        Expense expense = new Expense();
        expense.setDateDue(todaysDate);
        expense.setPeriod("monthly");
        expense.setPeriodDate("Last day of month");
        expense.setAssigned(BigDecimal.TEN);
        Account account = new Account();
        account.setCurrentAmount(BigDecimal.valueOf(100));
        expense.setAccount(account);

        when(expenseService.findAllByDateDue(todaysDate)).thenReturn(Collections.singletonList(expense));

        // Act
        expenseUpdater.updateExpense();

        // Assert
        verify(transactionService, times(1)).addExpenseTransaction(expense, expense.getUser());
        verify(accountService, times(1)).saveAndFlush(account);
        verify(expenseService, times(1)).saveAndFlush(expense);

        // Check that the new date due is the last day of the next month
        LocalDate expectedDateDue = todaysDate.plusMonths(1).withDayOfMonth(todaysDate.plusMonths(1).lengthOfMonth());
        assertEquals(expectedDateDue, expense.getDateDue());
    }

    @Test
    void updateExpenseCustomOrYearly() {
        LocalDate todaysDate = LocalDate.now();
        Expense expenseCustom = new Expense();
        expenseCustom.setDateDue(todaysDate);
        expenseCustom.setPeriod("custom");
        expenseCustom.setAssigned(BigDecimal.TEN);
        Account accountCustom = new Account();
        accountCustom.setCurrentAmount(BigDecimal.valueOf(100));
        expenseCustom.setAccount(accountCustom);

        Expense expenseYearly = new Expense();
        expenseYearly.setDateDue(todaysDate);
        expenseYearly.setPeriod("yearly");
        expenseYearly.setAssigned(BigDecimal.TEN);
        Account accountYearly = new Account();
        accountYearly.setCurrentAmount(BigDecimal.valueOf(100));
        expenseYearly.setAccount(accountYearly);

        when(expenseService.findAllByDateDue(todaysDate)).thenReturn(Arrays.asList(expenseCustom, expenseYearly));

        expenseUpdater.updateExpense();

        verify(transactionService, times(1)).addExpenseTransaction(expenseCustom, expenseCustom.getUser());
        verify(transactionService, times(1)).addExpenseTransaction(expenseYearly, expenseYearly.getUser());
        verify(accountService, times(2)).saveAndFlush(any(Account.class));
        verify(expenseService, times(1)).delete(expenseCustom);
        verify(expenseService, times(1)).saveAndFlush(expenseYearly);
    }
}