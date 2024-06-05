package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.category.CategoryService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController extends BaseController{

    public CategoryController(List<ExpenseDTO> expenses, UserService userService, ExpenseService expenseService, CategoryService categoryService, AccountService accountService, GoalsService goalsService, TransactionService transactionService) {
        super(expenses, userService, expenseService, accountService, goalsService, transactionService, categoryService);

    }

    @PostMapping("/addCategory")
    public String addCategory(@Valid AddCategoryDTO addCategoryDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "/homePage";
        }
        categoryService.addCategory(addCategoryDTO);
        return "redirect:/homePage";
    }
}
