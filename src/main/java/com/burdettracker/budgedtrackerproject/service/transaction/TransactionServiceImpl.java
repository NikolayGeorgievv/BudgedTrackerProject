package com.burdettracker.budgedtrackerproject.service.transaction;

import com.burdettracker.budgedtrackerproject.model.dto.transaction.AccountTransactionsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.TransactionInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;
import com.burdettracker.budgedtrackerproject.model.entity.Transaction;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addExpenseTransaction(Expense ex) {
        Transaction transaction = new Transaction(
                ex.getName(),
                ex.getAssigned(),
                ex.getDateDue(),
                ex.getAccount());

        //TODO: think about displaying the local date(dateDue) user-friendly.

        String transactionDescription = String.format("%s paid on %s. $%s withdrawn from account %s.",
                ex.getName(),
                ex.getDateDue(),
                ex.getAssigned(),
                ex.getAccount().getName());

        transaction.setTransactionDescription(transactionDescription);
        this.transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public AccountTransactionsDTO getAccountTransaction(String accountId) {
        Account account = this.accountRepository.getReferenceById(Long.valueOf(accountId));
        List<Transaction> transactionHistory = account.getExpenseTransactionHistory();

        List<TransactionInfoDTO> transactionsList = Arrays.stream(modelMapper.map(transactionHistory, TransactionInfoDTO[].class)).toList();
        AccountTransactionsDTO accountTransactionsDTO = new AccountTransactionsDTO(transactionsList);
        return accountTransactionsDTO;
    }

    @Override
    public void addGoalTransaction(Goal goal, BigDecimal addedAmount) {
        Transaction transaction = new Transaction(
                goal.getName(),
                addedAmount,
                LocalDate.now(),
                goal.getAccount()
        );

        //TODO: think about displaying the local date(dateDue) user-friendly.

        String transactionDescription = String.format("Successfully added $%s to your %s Goal. Date: %s. Account: %s.",
                addedAmount,
                goal.getName(),
                LocalDate.now(),
                goal.getAccountToUse()
              );

        transaction.setTransactionDescription(transactionDescription);
        this.transactionRepository.saveAndFlush(transaction);
    }
}
