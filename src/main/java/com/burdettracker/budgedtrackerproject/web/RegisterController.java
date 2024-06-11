package com.burdettracker.budgedtrackerproject.web;


import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Member;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("memberships")
    public MembershipType[] memberships() {
        return MembershipType.values();
    }


    @PostMapping("/users/register")
    public String register(@Valid @ModelAttribute("registerUserDTO") RegisterUserDTO registerUserDTO, BindingResult bindingResult, RedirectAttributes rAtt) {
        boolean passwordsNotMatch = !registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword());

        if (passwordsNotMatch){
            bindingResult.addError(new FieldError("registerUserDTO", "confirmPassword", "Passwords should match."));
        }

        if (bindingResult.hasErrors()){
            rAtt.addFlashAttribute("registerUserDTO", registerUserDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.RegisterUserDTO", bindingResult);

            return "registerForm";
        }

        try {
        userService.registerUser(registerUserDTO);
        }catch (RuntimeException err){
            bindingResult.addError(new FieldError("registerUserDTO", "invalidEmail", "Invalid email"));
            return "registerForm";
        }
        return "login";
    }

    @GetMapping("/users/register")
    public String register(RegisterUserDTO registerUserDTO) {

        return "registerForm";
    }

    @GetMapping("/users/termsAndConditions.html")
    public String termsAndConditions(){
        return "termsAndConditions.html";
    }
}
