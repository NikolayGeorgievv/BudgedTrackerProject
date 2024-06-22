package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("myEmail@example.com")
class ExpensesControllerTest {

    @Test
    void addExpense() {
        ExpenseDTO expenseDTO = new ExpenseDTO();

    }

    @Test
    void deleteExpense() {
    }

    @Test
    void editAccount() {
    }

    @Test
    void sortByCategory() {
    }
}