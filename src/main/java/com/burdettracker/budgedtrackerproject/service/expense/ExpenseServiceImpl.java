package com.burdettracker.budgedtrackerproject.service.expense;

import com.burdettracker.budgedtrackerproject.model.dto.expense.EditExpenseInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.burdettracker.budgedtrackerproject.util.Utils.setMonthlyDateDue;
import static com.burdettracker.budgedtrackerproject.util.Utils.setWeeklyDateDue;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ModelMapper modelMapper) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void deleteById(String expenseId) {
        this.expenseRepository.deleteById(Long.parseLong(expenseId));
    }

    @Override
    public void editExpense(EditExpenseInfoDTO editExpenseInfoDTO) {
        Optional<Expense> expenseToEditOpt = this.expenseRepository.findById(Long.parseLong(editExpenseInfoDTO.getId()));

        if (expenseToEditOpt.isPresent()) {
            Expense expenseToEdit = expenseToEditOpt.get();

            if (!expenseToEdit.getName().equals(editExpenseInfoDTO.getName()) && !editExpenseInfoDTO.getName().trim().equals("")) {
                expenseToEdit.setName(editExpenseInfoDTO.getName());
            }

            if (!expenseToEdit.getPeriod().equals(editExpenseInfoDTO.getPeriod()) && !editExpenseInfoDTO.getPeriod().trim().equals("")) {
                expenseToEdit.setPeriod(editExpenseInfoDTO.getPeriod());
            }

            if (!expenseToEdit.getPeriodDate().equals(editExpenseInfoDTO.getPeriodDate()) && !editExpenseInfoDTO.getPeriod().trim().equals("")) {
                LocalDate todaysDate = LocalDate.now();
                if (editExpenseInfoDTO.getPeriod().equals("weekly")) {

                    setWeeklyDateDue(expenseToEdit, editExpenseInfoDTO.getPeriodDate());

                } else if (editExpenseInfoDTO.getPeriod().equals("monthly")) {

                    setMonthlyDateDue(expenseToEdit, editExpenseInfoDTO.getPeriodDate());
                }


                expenseToEdit.setPeriodDate(editExpenseInfoDTO.getPeriodDate());
            }

            if (editExpenseInfoDTO.getAssigned() != null && !expenseToEdit.getAssigned().equals(editExpenseInfoDTO.getAssigned())) {
                expenseToEdit.setAssigned(editExpenseInfoDTO.getAssigned());
            }

            if (!expenseToEdit.getAccount().getName().equals(editExpenseInfoDTO.getAccountToUse())) {
                Account account = expenseToEdit.getUser().getAccounts()
                        .stream().filter(acc -> acc.getName().equals(editExpenseInfoDTO.getAccountToUse())).findFirst().get();

                expenseToEdit.setAccount(account);
            }

            if (!editExpenseInfoDTO.getDateDue().trim().equals("")) {
                String dateToSet = parseDateDue(editExpenseInfoDTO.getDateDue());
                expenseToEdit.setDateDue(LocalDate.parse(dateToSet));
                expenseToEdit.setPeriodDate(editExpenseInfoDTO.getDateDue());
            }


            this.expenseRepository.saveAndFlush(expenseToEdit);
        } else {
            throw new RuntimeException("Expense not found");
        }
    }


    @Override
    public List<ExpenseDTO> sortByCategory(String category) {
        Optional<List<Expense>> expensesByCategory = this.expenseRepository.findAllByCategory_Category(category);
        return expensesByCategory.map(expenses -> Arrays.stream(modelMapper.map(expenses, ExpenseDTO[].class)).toList()).orElse(null);
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

    @Override
    public void deleteByAccountId(long id) {
        this.expenseRepository.deleteByAccountId(id);
    }

    @Override
    public List<Expense> findAllByDateDue(LocalDate todaysDate) {
        return this.expenseRepository.findAllByDateDue(todaysDate);
    }

    @Override
    public void delete(Expense ex) {
        this.expenseRepository.delete(ex);
    }


    protected String parseDateDue(String dateToParse) {

        String[] dateAsString = dateToParse.split("-");
        String year = dateAsString[2];
        String month = dateAsString[1];
        String day = dateAsString[0];


        switch (month) {
            case "January" -> month = "01";
            case "February" -> month = "02";
            case "March" -> month = "03";
            case "April" -> month = "04";
            case "May" -> month = "05";
            case "June" -> month = "06";
            case "July" -> month = "07";
            case "August" -> month = "08";
            case "September" -> month = "09";
            case "October" -> month = "10";
            case "November" -> month = "11";
            case "December" -> month = "12";
        }

        return String.format("%s-%s-%s", year, month, day);
    }
}
