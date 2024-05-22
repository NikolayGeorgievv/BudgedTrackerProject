package com.burdettracker.budgedtrackerproject.service.csvWriter;

import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Transaction;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVService {


    private final AccountRepository accountRepository;

    public CSVService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Transaction> getAccountTransactions(String accountId){
        Account account = this.accountRepository.getReferenceById(Long.valueOf(accountId));
        List<Transaction> transactions = account.getExpenseTransactionHistory();
        return transactions;
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

        // Write data to response output stream
        try (OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream())) {
            CSVHelper.writeDataToCSV(writer, accountData);
        }
    }
}
