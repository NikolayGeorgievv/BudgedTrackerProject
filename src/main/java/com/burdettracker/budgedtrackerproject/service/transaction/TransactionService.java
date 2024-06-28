package com.burdettracker.budgedtrackerproject.service.transaction;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.AccountTransactionsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.TransactionInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.*;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void addExpenseTransaction(Expense ex, User user);

    AccountTransactionsDTO getAccountTransaction(String accountId);

    void fundGoalTransaction(Goal goal, BigDecimal addedAmount, User user, String newAccountToUse);

    void AddFundsTransaction(EditAccountInfoDTO editAccountInfoDTO, Account account, User user);

    List<TransactionInfoDTO> getAllTransactionsInfo(String currentUserName);

    Transaction createAccountTransaction(AccountDTO accountDTO);

    Transaction createGoalTransaction(Goal goal);

    void saveAllAndFlush(List<Transaction> expenseTransactionHistory);

    void saveAndFlush(Transaction transaction);
}
