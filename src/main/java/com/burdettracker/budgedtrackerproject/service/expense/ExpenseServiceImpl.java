package com.burdettracker.budgedtrackerproject.service.expense;

import com.burdettracker.budgedtrackerproject.model.dto.expense.EditExpenseInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final AccountRepository accountRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, AccountRepository accountRepository) {
        this.expenseRepository = expenseRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public void deleteById(String expenseId) {
        this.expenseRepository.deleteById(Long.parseLong(expenseId));
    }

    @Override
    public void editExpense(EditExpenseInfoDTO editExpenseInfoDTO) {
        Expense expenseToEdit = this.expenseRepository.getReferenceById(Long.parseLong(editExpenseInfoDTO.getId()));

        if (!expenseToEdit.getName().equals(editExpenseInfoDTO.getName()) && !editExpenseInfoDTO.getName().trim().equals("")) {
            expenseToEdit.setName(editExpenseInfoDTO.getName());}

        if (!expenseToEdit.getPeriod().equals(editExpenseInfoDTO.getPeriod()) && !editExpenseInfoDTO.getPeriod().trim().equals("")) {
            expenseToEdit.setPeriod(editExpenseInfoDTO.getPeriod());}

        if (!expenseToEdit.getPeriodDate().equals(editExpenseInfoDTO.getPeriodDate()) && !editExpenseInfoDTO.getPeriod().trim().equals("")) {
            expenseToEdit.setPeriodDate(editExpenseInfoDTO.getPeriodDate());}

        if (editExpenseInfoDTO.getAssigned() != null && !expenseToEdit.getAssigned().equals(editExpenseInfoDTO.getAssigned())) {
            expenseToEdit.setAssigned(editExpenseInfoDTO.getAssigned());}

        if (!expenseToEdit.getAccount().getName().equals(editExpenseInfoDTO.getAccountToUse())) {
            Account account = expenseToEdit.getUser().getAccounts()
                    .stream().filter(acc -> acc.getName().equals(editExpenseInfoDTO.getAccountToUse())).findFirst().get();

            expenseToEdit.setAccount(account);
        }

        if (!editExpenseInfoDTO.getDateDue().trim().equals("")) {
            // TODO: CHECK THE DATE IF IT IS IN THE FUTURE
            String dateToSet = parseDateDue(editExpenseInfoDTO.getDateDue());
            expenseToEdit.setDateDue(LocalDate.parse(dateToSet));
            expenseToEdit.setPeriodDate(editExpenseInfoDTO.getDateDue());}


        this.expenseRepository.saveAndFlush(expenseToEdit);
    }



private String parseDateDue(String dateToParse){
        //2024-May-11
    String[] dateAsString = dateToParse.split("-");
    String year = dateAsString[2];
    String month = dateAsString[1];
    String day = dateAsString[0];

    //check if the given date is in the future
//    if (!LocalDate.of(
//            Integer.parseInt(year),
//            Month.valueOf(month),
//            Integer.parseInt(day)).isAfter(LocalDate.now())){
//        throw new RuntimeException("Date should be in the future!");
//    }

    switch (month){
        case "January": month = "01"; break;
        case "February": month = "02"; break;
        case "March": month = "03"; break;
        case "April": month = "04"; break;
        case "May": month = "05"; break;
        case "June": month = "06"; break;
        case "July": month = "07"; break;
        case "August": month = "08"; break;
        case "September": month = "09"; break;
        case "October": month = "10"; break;
        case "November": month = "11"; break;
        case "December": month = "12"; break;
    }

        return String.format("%s-%s-%s",year, month,day);
    }
}
