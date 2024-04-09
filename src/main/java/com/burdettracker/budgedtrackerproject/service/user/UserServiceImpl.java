package com.burdettracker.budgedtrackerproject.service.user;

import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Address;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.AddressRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) {
        User user = new User();
        user.setMembershipType(registerUserDTO.getMembership());
        user.setFirstName(registerUserDTO.getFirstName());
        user.setLastName(registerUserDTO.getLastName());
        user.setEmail(registerUserDTO.getEmail());
        user.setPhoneNumber(registerUserDTO.getPhoneNumber());
        user.setTotalBalance(new BigDecimal(0));
        user.setPassword(registerUserDTO.getPassword());
        user.setAccounts(new ArrayList<>());
        user.setUserDocuments(new ArrayList<>());
        user.setGoals(new ArrayList<>());
        user.setExpenses(new ArrayList<>());

        Address address = new Address();
        address.setCity(registerUserDTO.getCity());
        address.setCountry(registerUserDTO.getCountry());
        address.setStreetName(registerUserDTO.getAddressStreet());
        address.setStreetNumber(registerUserDTO.getStreetNumber());
        List<User> userList = new ArrayList<>();
        userList.add(user);
        address.setUser(userList);

        user.setAddress(address);
        addressRepository.saveAndFlush(address);
        userRepository.saveAndFlush(user);

        User user1 = modelMapper.map(registerUserDTO, User.class);

    }
}
