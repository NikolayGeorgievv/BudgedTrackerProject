package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.UserExpensesDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserFullNameDTO;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Random;

@Controller
public class MainPageController {

    private final UserService userService;
    private List<ExpenseDTO> expenses;

    public MainPageController(UserService userService, List<ExpenseDTO> expenses) {
        this.userService = userService;
        this.expenses = expenses;
    }


    @ModelAttribute
    @GetMapping("/index")
    public String loggedIn(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        UserExpensesDetailsDTO userByEmail = userService.getUserByEmail(currentUserName);
        UserFullNameDTO userFullNameDTO = new UserFullNameDTO(userByEmail.getFirstName(), userByEmail.getLastName());
        model.addAttribute("userFullNameDTO", userFullNameDTO);


        expenses = userByEmail.getExpenses();
        model.addAttribute("userExpenses", expenses);

        //Use this random number to initialize date-pickers for each row.
        int random = new Random().nextInt();
        model.addAttribute("randomNumber", random);

        return "index";
    }

    @PostMapping("/update-data")
    public String updateData(UserExpensesDTO userExpensesDTO){

        ExpenseDTO expenseDTO = expenses.get(0);

        System.out.println("test");
        return null;
    }
}