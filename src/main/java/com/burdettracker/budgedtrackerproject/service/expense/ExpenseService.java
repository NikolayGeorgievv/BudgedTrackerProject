package com.burdettracker.budgedtrackerproject.service.expense;

import com.burdettracker.budgedtrackerproject.model.dto.expense.EditExpenseInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;

public interface ExpenseService {
    void deleteById(String expenseId);

    void editExpense(EditExpenseInfoDTO editExpenseInfoDTO);
}
