package com.burdettracker.budgedtrackerproject.service.transaction;

import com.burdettracker.budgedtrackerproject.model.dto.transaction.AccountTransactionsDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;

public interface TransactionService {
    void addTransaction(Expense ex);

    AccountTransactionsDTO getAccountTransaction(String accountId);
}
