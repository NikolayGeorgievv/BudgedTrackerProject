package com.burdettracker.budgedtrackerproject.service.transaction;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.AccountTransactionsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.TransactionInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.*;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.TransactionRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void addExpenseTransaction(Expense ex, User user) {
        Transaction transaction = new Transaction(
                ex.getName(),
                ex.getAssigned(),
                ex.getDateDue(),
                ex.getAccount());


        String transactionDescription = String.format("%s paid on %s.Category: %s. $%s withdrawn from account %s.",
                ex.getName(),
                ex.getDateDue(),
                ex.getCategory().getCategory(),
                ex.getAssigned(),
                ex.getAccount().getName());

        transaction.setTransactionDescription(transactionDescription);
        user.getTransactions().add(transaction);
        this.transactionRepository.saveAndFlush(transaction);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public AccountTransactionsDTO getAccountTransaction(String accountId) {
        Account account = this.accountRepository.getReferenceById(Long.valueOf(accountId));
        List<Transaction> transactionHistory = account.getExpenseTransactionHistory();

        List<TransactionInfoDTO> transactionsList = new java.util.ArrayList<>(Arrays.stream(modelMapper.map(transactionHistory, TransactionInfoDTO[].class)).toList());
        Collections.reverse(transactionsList);
        AccountTransactionsDTO accountTransactionsDTO = new AccountTransactionsDTO(transactionsList);
        return accountTransactionsDTO;
    }

    @Override
    public void fundGoalTransaction(Goal goal, BigDecimal addedAmount, User user, String newAccountToUse) {
        Transaction transaction = new Transaction(
                goal.getName(),
                addedAmount,
                LocalDate.now(),
                goal.getAccount()
        );


        String transactionDescription = String.format("Successfully added $%s to your %s Goal. Date: %s. Account: %s.",
                addedAmount,
                goal.getName(),
                LocalDate.now(),
                newAccountToUse
        );

        transaction.setTransactionDescription(transactionDescription);
        this.transactionRepository.saveAndFlush(transaction);
        user.getTransactions().add(transaction);
        this.transactionRepository.saveAndFlush(transaction);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void AddFundsTransaction(EditAccountInfoDTO editAccountInfoDTO, Account account, User user) {
        Transaction transaction = new Transaction(
                account.getName(),
                editAccountInfoDTO.getAddedAmount(),
                LocalDate.now(),
                account);
        String transactionDescription = String.format("Successfully added $%s to %s account. Date: %s.",
                editAccountInfoDTO.getAddedAmount(),
                account.getName(),
                LocalDate.now()
        );

        transaction.setTransactionDescription(transactionDescription);
        user.getTransactions().add(transaction);
        this.transactionRepository.saveAndFlush(transaction);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public List<TransactionInfoDTO> getAllTransactionsInfo(String email) {
        User user = this.userRepository.getByEmail(email);
        List<TransactionInfoDTO> transactionInfoDTOS = new java.util.ArrayList<>(Arrays.stream(modelMapper.map(user.getTransactions(), TransactionInfoDTO[].class)).toList());
        Collections.reverse(transactionInfoDTOS);
        return transactionInfoDTOS;
    }

    @Override
    public Transaction createAccountTransaction(AccountDTO accountDTO) {
        Account account = accountRepository.getByName(accountDTO.getName());
        Transaction transaction = new Transaction(
                accountDTO.getName(),
                accountDTO.getCurrentAmount(),
                LocalDate.now(),
                account);
        String transactionDescription = String.format("Successfully created %s account with $%s. Date: %s.",
                accountDTO.getName(),
                accountDTO.getCurrentAmount(),
                LocalDate.now()
        );
        transaction.setTransactionDescription(transactionDescription);
        return transaction;

    }

    @Override
    public Transaction createGoalTransaction(Goal goal) {
        Transaction transaction = new Transaction(
                goal.getName(),
                goal.getCurrentAmount(),
                LocalDate.now(),
                goal.getAccount());
        String transactionDescription = String.format("Successfully created %s goal with $%s. Date: %s.",
                goal.getName(),
                goal.getCurrentAmount(),
                LocalDate.now()
        );
        transaction.setTransactionDescription(transactionDescription);
        return transaction;

    }

    @Override
    public void saveAllAndFlush(List<Transaction> expenseTransactionHistory) {
        this.transactionRepository.saveAllAndFlush(expenseTransactionHistory);
    }

    @Override
    public void saveAndFlush(Transaction transaction) {
        transactionRepository.saveAndFlush(transaction);
    }
}


