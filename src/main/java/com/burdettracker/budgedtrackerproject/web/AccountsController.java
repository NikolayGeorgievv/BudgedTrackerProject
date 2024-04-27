package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.entity.enums.CurrencyType;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
//@RequestMapping("/index")
public class AccountsController {

    private final UserService userService;

    public AccountsController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("currencyTypes")
    public CurrencyType[] currencyTypes() {
        return CurrencyType.values();
    }

    @PostMapping("/addAccount")
    public String addAccount(
             @ModelAttribute("accountDTO") AccountDTO accountDTO, BindingResult bindingResult, RedirectAttributes rAtt){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        this.userService.addAccount(currentUserName, accountDTO);

        return "redirect:/index";
    }
}
