package com.burdettracker.budgedtrackerproject.service.expense;

import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExpenseServiceImplTest {
    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByDateDueReturnsCorrectData() {
        LocalDate date = LocalDate.now();
        List<Expense> expenses = Arrays.asList(new Expense(), new Expense());
        when(expenseRepository.findAllByDateDue(date)).thenReturn(expenses);

        List<Expense> result = expenseService.findAllByDateDue(date);

        assertEquals(2, result.size());
        verify(expenseRepository).findAllByDateDue(date);
    }

    @Test
    void deleteRemovesExpense() {
        Expense expense = new Expense();

        expenseService.delete(expense);

        verify(expenseRepository).delete(expense);
    }

    @Test
    void parseDateDueReturnsCorrectFormat() {
        String dateToParse = "01-January-2024";

        String result = expenseService.parseDateDue(dateToParse);

        assertEquals("2024-01-01", result);
    }


}