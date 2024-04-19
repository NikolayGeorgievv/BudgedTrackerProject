package com.burdettracker.budgedtrackerproject.service.user;

import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Address;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.AddressRepository;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ExpenseRepository expenseRepository;
    private final UserDetailImpl userDetail;
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, ExpenseRepository expenseRepository, UserDetailImpl userDetail) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.expenseRepository = expenseRepository;
        this.userDetail = userDetail;
    }

    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) {

        User user = mapUser(registerUserDTO);
        Address address = mapAddress(registerUserDTO, user);
        user.setAddress(address);
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        addressRepository.saveAndFlush(address);
        userRepository.saveAndFlush(user);
        expenseRepository.saveAllAndFlush(user.getExpenses());

    }

    @Override
    public Authentication login(String email) {
        UserDetails userDetails = userDetail.loadUserByUsername(email);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    @Override
    public UserExpensesDetailsDTO getUserByEmail(String email) {
        User userByEmail = userRepository.getByEmail(email);
       return modelMapper.map(userByEmail, UserExpensesDetailsDTO.class);
    }

    private User mapUser(RegisterUserDTO registerUserDTO){

        User user = modelMapper.map(registerUserDTO, User.class);
        //Set basic expenses for the newly registered user
        user.setExpenses(assignBasicExpenses(user));
        return user;

    }

    private Address mapAddress(RegisterUserDTO registerUserDTO, User user){

        Address address = modelMapper.map(registerUserDTO, Address.class);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        address.setUser(userList);

        return address;
    }

    public List<Expense> assignBasicExpenses(User user){
        List<Expense> expenses = user.getExpenses();
        expenses.add(new Expense("Electricity", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));
        expenses.add(new Expense("Water", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));
        expenses.add(new Expense("Phone", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));
        expenses.add(new Expense("Internet", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));
        expenses.add(new Expense("Car", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));
        expenses.add(new Expense("Fitness", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));
        expenses.add(new Expense("Groceries", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));
        expenses.add(new Expense("Train/Bus fee", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));
        expenses.add(new Expense("Home maintenance", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));
        expenses.add(new Expense("Investments", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));
        expenses.add(new Expense("Entertainment", LocalDate.now() , BigDecimal.ZERO, BigDecimal.ZERO, user));

        return expenses;
    }
}
