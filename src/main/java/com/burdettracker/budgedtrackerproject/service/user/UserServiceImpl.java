package com.burdettracker.budgedtrackerproject.service.user;

import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.model.entity.Transaction;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.model.entity.enums.CurrencyType;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import com.burdettracker.budgedtrackerproject.repository.TransactionRepository;
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
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ExpenseRepository expenseRepository;
    private final UserDetailImpl userDetail;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, ExpenseRepository expenseRepository, UserDetailImpl userDetail, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.expenseRepository = expenseRepository;
        this.userDetail = userDetail;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) {

        User user = mapUser(registerUserDTO);

        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        userRepository.saveAndFlush(user);
        expenseRepository.saveAllAndFlush(user.getExpenses());
        transactionRepository.saveAllAndFlush(user.getAccounts().get(0).getExpenseTransactionHistory());
        accountRepository.saveAllAndFlush(user.getAccounts());

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

        //Set users account limit based on his membership
        switch (user.getMembershipType()){
            case FREE -> user.setUserAccountsAllowed(1);
            case GOLD -> user.setUserAccountsAllowed(2);
            case PREMIUM -> user.setUserAccountsAllowed(20);
        }
        //Set basic account for the user
        List<Transaction> transactions = new ArrayList<>();
        Account baseAccount = new Account("MyAccount",LocalDate.now(), CurrencyType.LV,BigDecimal.ZERO,transactions, user);
        user.getAccounts().add(baseAccount);
        return user;
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
