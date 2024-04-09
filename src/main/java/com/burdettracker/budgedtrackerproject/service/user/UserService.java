package com.burdettracker.budgedtrackerproject.service.user;


import com.burdettracker.budgedtrackerproject.model.dto.user.LoginUserDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.User;

public interface UserService {
    void registerUser(RegisterUserDTO registerUserDTO);

    boolean login(LoginUserDTO loginUserDTO);

}
