package com.burdettracker.budgedtrackerproject.service.schedueledEvents;


import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class ExpenseUpdater {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final ExpenseService expenseService;

    public ExpenseUpdater(TransactionService transactionService, AccountService accountService, ExpenseService expenseService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.expenseService = expenseService;
    }

    //cron = */30.. is for test purposes only
    //    @Scheduled(cron = "*/30 * * * * *")
    @Scheduled(cron = "0 55 23 */1 * *")
    public void updateExpense() {
        LocalDate todaysDate = LocalDate.now();
        List<Expense> expensesByDateDue = this.expenseService.findAllByDateDue(todaysDate);
        expensesByDateDue.forEach(ex -> {

            transactionService.addExpenseTransaction(ex, ex.getUser());

            String period = ex.getPeriod();
            Account account = ex.getAccount();


            if (period.equals("weekly") || period.equals("monthly")) {
                if (period.equals("weekly")) {
                    LocalDate newDateToSet = ex.getDateDue().plusWeeks(1);
                    ex.setDateDue(newDateToSet);
                    BigDecimal newAccValue = account.getCurrentAmount().subtract(ex.getAssigned());
                    account.setCurrentAmount(newAccValue);

                } else {
                    LocalDate newDateToSet;
                    if (ex.getPeriodDate().equals("Last day of month")) {
                        newDateToSet = todaysDate.plusMonths(1).withDayOfMonth(todaysDate.plusMonths(1).lengthOfMonth());
                    } else {
                        newDateToSet = ex.getDateDue().plusMonths(1);
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
                }
            }
            this.accountService.saveAndFlush(account);

            //one-time-buys will be deleted from expenses(transaction will be made)
            if (period.equals("custom")) {
                this.expenseService.delete(ex);
            } else {
                this.expenseService.saveAndFlush(ex);
            }
        });

    }
}
