package com.burdettracker.budgedtrackerproject.web;


import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Member;

@Controller
public class RegisterController {

    private  final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

@ModelAttribute("memberships")
public MembershipType[] memberships() {
    return MembershipType.values();
}


    @PostMapping("users/register")
    public String register(RegisterUserDTO registerUserDTO) {
        userService.registerUser(registerUserDTO);

        return "index";
    }

    @GetMapping("/users/register")
    public String register(){

        return "registerForm";
    }
}
