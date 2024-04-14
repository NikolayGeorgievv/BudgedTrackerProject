package com.burdettracker.budgedtrackerproject.service.user;


import com.burdettracker.budgedtrackerproject.model.dto.user.LoginUserDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    void registerUser(RegisterUserDTO registerUserDTO);

    Authentication login(String email);

    User getUserByEmail(String email);

}
