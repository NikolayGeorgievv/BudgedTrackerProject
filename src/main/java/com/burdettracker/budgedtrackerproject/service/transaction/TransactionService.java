package com.burdettracker.budgedtrackerproject.service.transaction;

import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.AccountTransactionsDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;

import java.math.BigDecimal;

public interface TransactionService {
    void addExpenseTransaction(Expense ex);

    AccountTransactionsDTO getAccountTransaction(String accountId);

    void addGoalTransaction(Goal goal, BigDecimal addedAmount);

    void AddFundsTransaction(EditAccountInfoDTO editAccountInfoDTO, Account account);
}
