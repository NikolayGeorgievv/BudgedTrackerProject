package com.burdettracker.budgedtrackerproject.service.user;

import com.burdettracker.budgedtrackerproject.model.dto.user.LoginUserDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Address;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.AddressRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) {

        User user = mapUser(registerUserDTO);
        Address address = mapAddress(registerUserDTO, user);
        user.setAddress(address);
        addressRepository.saveAndFlush(address);
        userRepository.saveAndFlush(user);


    }

    @Override
    public boolean login(LoginUserDTO loginUserDTO) {
        Optional<User> userByEmail = userRepository.findByEmail(loginUserDTO.getEmail());
        if (userByEmail.isEmpty()){
            return false;
        }
        return passwordEncoder.matches(loginUserDTO.getPassword(), userByEmail.get().getPassword());
    }

    private User mapUser(RegisterUserDTO registerUserDTO){
        User user = new User();
        user.setMembershipType(registerUserDTO.getMembership());
        user.setFirstName(registerUserDTO.getFirstName());
        user.setLastName(registerUserDTO.getLastName());
        user.setEmail(registerUserDTO.getEmail());
        user.setPhoneNumber(registerUserDTO.getPhoneNumber());
        user.setTotalBalance(new BigDecimal(0));
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setAccounts(new ArrayList<>());
        user.setUserDocuments(new ArrayList<>());
        user.setGoals(new ArrayList<>());
        user.setExpenses(new ArrayList<>());

        return user;
    }

    private Address mapAddress(RegisterUserDTO registerUserDTO, User user){
        Address address = new Address();
        address.setCity(registerUserDTO.getCity());
        address.setCountry(registerUserDTO.getCountry());
        address.setStreetName(registerUserDTO.getAddressStreet());
        address.setStreetNumber(registerUserDTO.getStreetNumber());
        List<User> userList = new ArrayList<>();
        userList.add(user);
        address.setUser(userList);

        return address;
    }
}
