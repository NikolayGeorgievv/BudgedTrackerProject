package com.burdettracker.budgedtrackerproject.service.schedueledEvents;


import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class ExpenseUpdater {

    private final AccountRepository accountRepository;
    private final ExpenseRepository expenseRepository;
    private final TransactionService transactionService;

    public ExpenseUpdater(AccountRepository accountRepository, ExpenseRepository expenseRepository, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.expenseRepository = expenseRepository;
        this.transactionService = transactionService;
    }

    //cron = */30.. is for test purposes only

//    @Scheduled(cron = "*/30 * * * * *")
    @Scheduled(cron = "0 55 23 */1 * *")
    public void updateExpense() {
        LocalDate todaysDate = LocalDate.now();
        List<Expense> expensesByDateDue = this.expenseRepository.findAllByDateDue(todaysDate);
        expensesByDateDue.forEach(ex -> {

            transactionService.addTransaction(ex);

            String period = ex.getPeriod();
            Account account = ex.getAccount();

            if (period.equals("weekly") || period.equals("monthly")) {
                if (period.equals("weekly")) {
                    LocalDate newDateToSet = ex.getDateDue().plusWeeks(1);
                    ex.setDateDue(newDateToSet);
                    BigDecimal newAccValue = account.getCurrentAmount().subtract(ex.getAssigned());
                    account.setCurrentAmount(newAccValue);

                } else {
                    LocalDate newDateToSet = ex.getDateDue().plusMonths(1);
                    if (ex.getPeriodDate().equals("Last day of month")) {
                        //We only set this in case the period date is set to
                        //"Last day of month" and previous month has fewer days than the next one
                        LocalDate updatedNewDateToSet = LocalDate.of(
                                newDateToSet.getYear(),
                                newDateToSet.getMonth(),
                                newDateToSet.lengthOfMonth());
                        ex.setDateDue(updatedNewDateToSet);
                    }
                    ex.setDateDue(newDateToSet);
                    BigDecimal newAccValue = account.getCurrentAmount().subtract(ex.getAssigned());
                    account.setCurrentAmount(newAccValue);
                }

            } else if (period.equals("custom") || period.equals("yearly")) {

                if (period.equals("yearly")) {
                    LocalDate newDateToSet = ex.getDateDue().plusYears(1);
                    ex.setDateDue(newDateToSet);
                    BigDecimal newAccValue = account.getCurrentAmount().subtract(ex.getAssigned());
                    account.setCurrentAmount(newAccValue);

                } else {
                    BigDecimal newAccValue = account.getCurrentAmount().subtract(ex.getAssigned());
                    account.setCurrentAmount(newAccValue);
                    //TODO: FIGURE OUT WHAT TO DO WITH ONE-TIME-BUY EXPENSES
                }
            }

            accountRepository.saveAndFlush(account);
            expenseRepository.saveAndFlush(ex);
        });

    }
}
