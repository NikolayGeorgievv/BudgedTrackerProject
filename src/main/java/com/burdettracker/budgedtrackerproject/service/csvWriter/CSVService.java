package com.burdettracker.budgedtrackerproject.service.csvWriter;

import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Transaction;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    public CSVService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public List<Transaction> getAccountTransactions(String accountId) {
        Account account = this.accountRepository.getReferenceById(Long.valueOf(accountId));
        return account.getExpenseTransactionHistory();
    }

    public void generateCSV(HttpServletResponse response, String accountId) throws IOException {
        List<String[]> accountData = new ArrayList<>();
        List<Transaction> accountTransactions = getAccountTransactions(accountId);

        accountData.add(new String[]{"Paid", "Amount", "Date", "Description"});
        for (Transaction transaction : accountTransactions) {
            String[] row = new String[]{
                    transaction.getPaidTo(),
                    transaction.getAmount().toString(),
                    transaction.getCompletedOn().toString(),
                    transaction.getTransactionDescription()};
            accountData.add(row);
        }
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=account_transactions.csv");

        try (OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream())) {
            CSVHelper.writeDataToCSV(writer, accountData);
        }
    }

    public void generateAllCSV(HttpServletResponse response, String email) throws IOException {
        User user = this.userService.getUserByEmail(email);
        List<String[]> transactionData = new ArrayList<>();

        List<Transaction> transactions = user.getTransactions();
        transactionData.add(new String[]{"Paid", "Amount", "Date", "Description"});
        for (Transaction transaction : transactions) {
            String[] row = new String[]{
                    transaction.getPaidTo(),
                    transaction.getAmount().toString(),
                    transaction.getCompletedOn().toString(),
                    transaction.getTransactionDescription()};
            transactionData.add(row);
        }
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=all_accounts_transactions.csv");

        try (OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream())) {
            CSVHelper.writeDataToCSV(writer, transactionData);
        }
    }
}
