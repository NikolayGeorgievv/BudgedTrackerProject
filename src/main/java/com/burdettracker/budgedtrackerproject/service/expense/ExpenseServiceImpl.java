package com.burdettracker.budgedtrackerproject.service.expense;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final AccountService accountService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ModelMapper modelMapper, AccountService accountService) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
        this.accountService = accountService;
    }

    @Override
    public void addExpense(ExpenseDTO expenseDTO) {
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        //TODO: manually set dateDue
        Account accountToUse = accountService.getByName(expenseDTO.getAccountToUse());
        expense.setAccountToUse(accountToUse);

        if (expenseDTO.getPeriodDate().equals("")){
            //period = yearly or custom
            //dateDue will be used
            String[] dateData = expenseDTO.getDateDue().split("-");
            int year = Integer.parseInt(dateData[0]);
            String month = dateData[1].toUpperCase();
            int day = Integer.parseInt(dateData[2]);

            //check if the given date is in the future
            if (LocalDate.of(year, Month.valueOf(month), day).isAfter(LocalDate.now())){
            expense.setDateDue(LocalDate.of(year, Month.valueOf(month), day));
            }else {
                //TODO: throw error or bind an error to the binding result
            }
        } else if (expenseDTO.getDateDue().equals("")) {
            //period = weekly or monthly
            //period date will be used
            //day from period date, month = this month + 1, year = this year except if it is december

        }

        System.out.println("TEST");
        accountToUse.getExpenses().add(expense);
        accountService.updateAccountExpenses(accountToUse);
        expenseRepository.saveAndFlush(expense);
    }
}
