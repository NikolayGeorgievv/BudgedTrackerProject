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


}
