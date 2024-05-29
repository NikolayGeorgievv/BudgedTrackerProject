package com.burdettracker.budgedtrackerproject.service.user;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.membership.ChangeMembershipDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.*;
import com.burdettracker.budgedtrackerproject.model.entity.*;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.repository.*;
import com.burdettracker.budgedtrackerproject.service.email.EmailVerificationService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseServiceImpl;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static com.burdettracker.budgedtrackerproject.util.Utils.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ExpenseRepository expenseRepository;
    private final UserDetailImpl userDetail;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final GoalsRepository goalsRepository;
    private final RolesRepository rolesRepository;
    private final EmailVerificationService emailVerificationService;
    private final TransactionService transactionService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, ExpenseRepository expenseRepository, UserDetailImpl userDetail, AccountRepository accountRepository, TransactionRepository transactionRepository, GoalsRepository goalsRepository, RolesRepository rolesRepository, EmailVerificationService emailVerificationService, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.expenseRepository = expenseRepository;
        this.userDetail = userDetail;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.goalsRepository = goalsRepository;
        this.rolesRepository = rolesRepository;
        this.emailVerificationService = emailVerificationService;
        this.transactionService = transactionService;
    }

    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) {

        if (registerUserDTO.isENABLED()) {
            if (emailVerificationService.isEmailValid(registerUserDTO.getEmail())) {
                throw new RuntimeException("Invalid email.");
            }
        }

        User user = mapUser(registerUserDTO);

        //First registered user will be Admin, also populate roles table

        if (this.userRepository.count() == 0) {
            List<UserRoleEntity> allRoles = rolesRepository.findAll();
            user.setRoles(allRoles);
        } else {
            UserRoleEntity userRole = rolesRepository.getReferenceById(1L);
            user.setRoles(List.of(userRole));
        }


        //Set registeredOn, this will be used for account subscription reference
        user.setRegisteredOnDate(LocalDate.now());
        //Subscription fees will be deducted from the basic account
        user.setAccountNameAssignedForSubscription(user.getAccounts().get(0).getName());
        userRepository.saveAndFlush(user);
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
    public UserExpensesDetailsDTO getUserExpensesDetailsByEmail(String email) {
        User userByEmail = userRepository.getByEmail(email);
        return modelMapper.map(userByEmail, UserExpensesDetailsDTO.class);
    }


    private User mapUser(RegisterUserDTO registerUserDTO) {

        User user = modelMapper.map(registerUserDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

        //Set users account limit based on his membership
        switch (user.getMembershipType()) {
            case FREE -> user.setUserAccountsAllowed(1);
            case GOLD -> user.setUserAccountsAllowed(2);
            case PREMIUM -> user.setUserAccountsAllowed(10);
        }
        //Set basic account for the user
        List<Transaction> transactions = new ArrayList<>();
        Account baseAccount = new Account("MyAccount", LocalDate.now(), BigDecimal.ZERO, transactions, user);
        user.getAccounts().add(baseAccount);
        return user;
    }


    @Override
    public void addAccount(String email, AccountDTO accountDTO) {
        User user = this.userRepository.getByEmail(email);
        Account account = new Account(
                accountDTO.getName(),
                LocalDate.now(),
                accountDTO.getCurrentAmount(),
                new ArrayList<>(),
                user
        );
        Transaction transaction = this.transactionService.createAccountTransaction(accountDTO);
        transactionRepository.saveAndFlush(transaction);
        user.getTransactions().add(transaction);
        user.getAccounts().add(account);
        userRepository.saveAndFlush(user);
        account.getExpenseTransactionHistory().add(transaction);
        accountRepository.saveAndFlush(account);
    }

    @Override
    public void addExpense(String email, ExpenseDTO expenseDTO) {
        User user = this.userRepository.getByEmail(email);
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        Account accountToUse = user.getAccounts()
                .stream().filter(acc -> acc.getName().equals(expenseDTO.getAccountToUse())).findFirst().get();

        expense.setAccount(accountToUse);
        expense.setUser(user);

        if (expenseDTO.getPeriodDate().equals("")) {
            //period = yearly or one-time-buy
            //period date needs to be set
            String[] dateData = expenseDTO.getDateDue().split("-");
            int day = Integer.parseInt(dateData[0]);
            String monthNormalCasing = dateData[1];
            String month = dateData[1].toUpperCase();
            int year = Integer.parseInt(dateData[2]);

            //check if the given date is in the future
            if (LocalDate.of(year, Month.valueOf(month), day).isAfter(LocalDate.now()) || LocalDate.of(year, Month.valueOf(month), day).equals(LocalDate.now())) {
                //TODO: CHECK IF THE BILL IS ONE-TIME-BUY
                expense.setDateDue(LocalDate.of(year, Month.valueOf(month.toUpperCase()), day));
                expense.setPeriodDate(String.format("%d-%s-%d", day, monthNormalCasing, year));
            } else {
                throw new RuntimeException("Date should be in the future!");
            }
        } else if (expenseDTO.getDateDue().equals("")) {
            //period = weekly or monthly
            //dateDue needs to be set

            if (expenseDTO.getPeriod().equals("weekly")) {

                setWeeklyDateDue(expense, expenseDTO.getPeriodDate());

            } else if (expenseDTO.getPeriod().equals("monthly")) {

                setMonthlyDateDue(expense, expenseDTO.getPeriodDate());
            }
        }

        accountToUse.getExpenses().add(expense);
        accountRepository.saveAndFlush(accountToUse);
        user.getExpenses().add(expense);
        userRepository.saveAndFlush(user);
        expenseRepository.saveAndFlush(expense);
    }

    //TODO: TAKE THOSE METHODS IN A UTIL CLASS

    @Override
    public void addGoal(String email, GoalDTO goalDTO) {
        User user = this.userRepository.getByEmail(email);
        Account account = user.getAccounts()
                .stream().filter(acc -> acc.getName().equals(goalDTO.getAccountToUse())).findFirst().get();

        Goal goal = new Goal(
                goalDTO.getName(),
                goalDTO.getAmountToBeSaved(),
                goalDTO.getCurrentAmount(),
                goalDTO.getDescription(),
                user,
                account,
                goalDTO.getAccountToUse(),
                false,
                //Created on
                LocalDate.now(),
                //Completed on
                null);
        if (goal.getCurrentAmount() == null) {
            goal.setCurrentAmount(BigDecimal.ZERO);
        } else {
            if (goalDTO.getCurrentAmount().equals(goalDTO.getAmountToBeSaved())) {
                goal.setCompleted(true);
                goal.setCompletedOn(LocalDate.now());
            }
            BigDecimal newAccAmount = account.getCurrentAmount().subtract(goalDTO.getCurrentAmount());
            account.setCurrentAmount(newAccAmount);
        }
        Transaction transaction = this.transactionService.createGoalTransaction(goal);
        user.getTransactions().add(transaction);
        account.getExpenseTransactionHistory().add(transaction);
        user.getGoals().add(goal);
        transactionRepository.saveAndFlush(transaction);
        accountRepository.saveAndFlush(account);
        userRepository.saveAndFlush(user);
        goalsRepository.saveAndFlush(goal);
    }

    @Override
    public void changeUserPlan(ChangeMembershipDTO changePlanDTO, String email) {
        User user = userRepository.getByEmail(email);
        user.setAccountNameAssignedForSubscription(changePlanDTO.getAccountToUse());
        changeUserMembership(user, changePlanDTO.getMembership());

        userRepository.saveAndFlush(user);
    }

    @Override
    public AllUsersInfoDTO getAllUsersInfo() {
        List<User> allUsers = userRepository.findAll();
        List<UserFullDetailsInfoDTO> mappedUsers = Arrays.stream(modelMapper.map(allUsers, UserFullDetailsInfoDTO[].class)).toList();
        AllUsersInfoDTO allUsersInfoDTO = new AllUsersInfoDTO(mappedUsers);
        return allUsersInfoDTO;
    }

    @Override
    public AllUsersInfoDTO filterAllUsersByEmail(String email) {
        Optional<List<User>> allUsers = userRepository.findAllByEmailContaining(email);
        if (allUsers.isEmpty()) {
            //TODO: CATCH EX
            return null;
        }
        List<UserFullDetailsInfoDTO> mappedUsers = Arrays.stream(modelMapper.map(allUsers.get(), UserFullDetailsInfoDTO[].class)).toList();
        AllUsersInfoDTO allUsersInfoDTO = new AllUsersInfoDTO(mappedUsers);
        return allUsersInfoDTO;
    }

    @Override
    public UserFullDetailsInfoDTO getUserById(String userId) {
        User user = this.userRepository.getReferenceById(UUID.fromString(userId));
        UserFullDetailsInfoDTO mappedUser = modelMapper.map(user, UserFullDetailsInfoDTO.class);
        return mappedUser;
    }

    @Override
    public void updateUser(UserChangeInformationDTO userChangeInformationDTO) {
        User user = userRepository.getReferenceById(UUID.fromString(userChangeInformationDTO.getId()));

        if (!user.getMembershipType().toString().equals(userChangeInformationDTO.getMembership())){
            changeUserMembership(user, userChangeInformationDTO.getMembership());
        }
        if (!userChangeInformationDTO.getNewFirstName().trim().equals("")){
            user.setFirstName(userChangeInformationDTO.getNewFirstName());
        }
        if (!userChangeInformationDTO.getNewLastName().trim().equals("")){
            user.setLastName(userChangeInformationDTO.getNewLastName());
        }
        if (!userChangeInformationDTO.getNewPhoneNumber().trim().equals("")){
            user.setPhoneNumber(userChangeInformationDTO.getNewPhoneNumber());
        }
        if (userChangeInformationDTO.isPromoteUser()){
            UserRoleEntity ADMIN = rolesRepository.getReferenceById(2L);
            UserRoleEntity USER = rolesRepository.getReferenceById(1L);
            user.setRoles(List.of(ADMIN, USER));
        }
        if (userChangeInformationDTO.isDemoteAdmin()){
            UserRoleEntity USER = rolesRepository.getReferenceById(1L);
            user.setRoles(List.of(USER));
        }
        try {
            userRepository.saveAndFlush(user);

        }catch (UnsupportedOperationException ex){
            userRepository.saveAndFlush(user);

        }

    }

    private void changeUserMembership(User user, String membership) {
        switch (membership) {
            case "FREE":
                user.setMembershipType(MembershipType.FREE);
                user.setUserAccountsAllowed(1);
                break;
            case "GOLD":
                user.setMembershipType(MembershipType.GOLD);
                user.setUserAccountsAllowed(2);
                break;
            case "PREMIUM":
                user.setMembershipType(MembershipType.PREMIUM);
                user.setUserAccountsAllowed(10);
                break;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.getByEmail(email);
    }

}
