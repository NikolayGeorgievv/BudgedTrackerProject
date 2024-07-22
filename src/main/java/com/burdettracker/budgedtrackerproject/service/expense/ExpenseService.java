package com.burdettracker.budgedtrackerproject.service.expense;

import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.EditExpenseInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    void deleteById(String expenseId);

    void editExpense(EditExpenseInfoDTO editExpenseInfoDTO);

    List<ExpenseDTO> sortByCategory(String category);

    String getTotalValue(List<ExpenseDTO> sortedExpenses);


    void saveAndFlush(Expense expense);

    void deleteByAccountId(long id);

    List<Expense> findAllByDateDue(LocalDate todaysDate);

    void delete(Expense ex);
}
