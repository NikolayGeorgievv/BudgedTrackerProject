package com.burdettracker.budgedtrackerproject.service.user;


import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.User;

public interface UserService {
    User saveUserToDb(RegisterUserDTO registerUserDTO);
}
