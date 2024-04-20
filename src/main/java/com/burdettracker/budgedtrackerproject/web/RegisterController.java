package com.burdettracker.budgedtrackerproject.web;


import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String register(@Valid RegisterUserDTO registerUserDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            String fieldError = bindingResult.getFieldError().getField().toLowerCase();
            switch (fieldError) {
                case "firstname":
                    model.addAttribute("firstNameError", true);
                    break;
                case "lastname":
                    model.addAttribute("lastNameError", true);
                    break;
                case "email":
                    model.addAttribute("emailError", true);
                    break;
                case "phonenumber":
                    model.addAttribute("phoneNumberError", true);
                    break;
                case "password":
                    model.addAttribute("passwordError", true);
                    break;
                case "termsaccepted":
                    model.addAttribute("termsAccepted", true);
                    break;
            }
            if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())) {
                model.addAttribute("confirmPasswordError", true);
            }

            return "registerForm";
        }

        userService.registerUser(registerUserDTO);

        return "index";
    }

    @GetMapping("/users/register")
    public String register() {

        return "registerForm";
    }
}
