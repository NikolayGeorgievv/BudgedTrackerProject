package com.burdettracker.budgedtrackerproject.service.expense;

import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.EditExpenseInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.model.entity.enums.ExpenseCategories;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.burdettracker.budgedtrackerproject.util.Utils.*;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, AccountRepository accountRepository, ModelMapper modelMapper) {
        this.expenseRepository = expenseRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
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
            LocalDate todaysDate = LocalDate.now();
            if (editExpenseInfoDTO.getPeriod().equals("weekly")){
                //30th, monthly 2024-05-30
                //Thursday, weekly next thursday
                setWeeklyDateDue(expenseToEdit, editExpenseInfoDTO.getPeriodDate());

            } else if (editExpenseInfoDTO.getPeriod().equals("monthly")) {

                setMonthlyDateDue(expenseToEdit, editExpenseInfoDTO.getPeriodDate());
            }


            expenseToEdit.setPeriodDate(editExpenseInfoDTO.getPeriodDate());}

        if (editExpenseInfoDTO.getAssigned() != null && !expenseToEdit.getAssigned().equals(editExpenseInfoDTO.getAssigned())) {
            expenseToEdit.setAssigned(editExpenseInfoDTO.getAssigned());}

        if (!expenseToEdit.getAccount().getName().equals(editExpenseInfoDTO.getAccountToUse())) {
            Account account = expenseToEdit.getUser().getAccounts()
                    .stream().filter(acc -> acc.getName().equals(editExpenseInfoDTO.getAccountToUse())).findFirst().get();

            expenseToEdit.setAccount(account);
        }

        if (!editExpenseInfoDTO.getDateDue().trim().equals("")) {
            String dateToSet = parseDateDue(editExpenseInfoDTO.getDateDue());
            expenseToEdit.setDateDue(LocalDate.parse(dateToSet));
            expenseToEdit.setPeriodDate(editExpenseInfoDTO.getDateDue());}


        this.expenseRepository.saveAndFlush(expenseToEdit);
    }



    @Override
    public List<Expense> findByDateDue(LocalDate todaysDate) {
        return this.expenseRepository.findAllByDateDue(todaysDate);
    }

    @Override
    public List<ExpenseDTO> sortByCategory(String category) {
        Optional<List<Expense>> expensesByCategory = this.expenseRepository.findAllByCategory_Category(category);
        if (expensesByCategory.isEmpty()){
            return null;
        }
        return Arrays.stream(modelMapper.map(expensesByCategory.get(), ExpenseDTO[].class)).toList();
    }

    @Override
    public String getTotalValue(List<ExpenseDTO> expenses) {
        double totalBalance = expenses.stream().mapToDouble(e -> Double.parseDouble(String.valueOf(e.getAssigned()))).sum();
        return String.valueOf(totalBalance);
    }

    @Override
    public void saveAndFlush(Expense expense) {
        expenseRepository.saveAndFlush(expense);
    }


    private String parseDateDue(String dateToParse){

    String[] dateAsString = dateToParse.split("-");
    String year = dateAsString[2];
    String month = dateAsString[1];
    String day = dateAsString[0];


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
