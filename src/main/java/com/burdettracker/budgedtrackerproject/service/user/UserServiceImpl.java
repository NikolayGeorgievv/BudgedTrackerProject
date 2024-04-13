package com.burdettracker.budgedtrackerproject.service.user;

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

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) {

        User user = mapUser(registerUserDTO);
        Address address = mapAddress(registerUserDTO, user);
        user.setAddress(address);
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        addressRepository.saveAndFlush(address);
        userRepository.saveAndFlush(user);
    }

    private User mapUser(RegisterUserDTO registerUserDTO){

        return modelMapper.map(registerUserDTO, User.class);


//        User user = new User();
//        user.setMembershipType(registerUserDTO.getMembership());
//        user.setFirstName(registerUserDTO.getFirstName());
//        user.setLastName(registerUserDTO.getLastName());
//        user.setEmail(registerUserDTO.getEmail());
//        user.setPhoneNumber(registerUserDTO.getPhoneNumber());
//        user.setTotalBalance(new BigDecimal(0));
//        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
//        user.setAccounts(new ArrayList<>());
//        user.setUserDocuments(new ArrayList<>());
//        user.setGoals(new ArrayList<>());
//        user.setExpenses(new ArrayList<>());

//        return user;
    }

    private Address mapAddress(RegisterUserDTO registerUserDTO, User user){

        Address address = modelMapper.map(registerUserDTO, Address.class);

//        Address address = new Address();
//        address.setCity(registerUserDTO.getCity());
//        address.setCountry(registerUserDTO.getCountry());
//        address.setStreetName(registerUserDTO.getAddressStreet());
//        address.setStreetNumber(registerUserDTO.getStreetNumber());
        List<User> userList = new ArrayList<>();
        userList.add(user);
        address.setUser(userList);

        return address;
    }
}
