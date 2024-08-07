package com.burdettracker.budgedtrackerproject.service.user;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.membership.ChangeMembershipDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.*;
import com.burdettracker.budgedtrackerproject.model.entity.*;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.repository.CategoryRepository;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.email.EmailVerificationService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static com.burdettracker.budgedtrackerproject.util.Utils.setMonthlyDateDue;
import static com.burdettracker.budgedtrackerproject.util.Utils.setWeeklyDateDue;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserDetailImpl userDetail;
    private final RolesRepository rolesRepository;
    private final EmailVerificationService emailVerificationService;
    private final TransactionService transactionService;
    private final CategoryRepository categoryRepository;
    private final AccountService accountService;
    private final ExpenseService expenseService;
    private final GoalsService goalsService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserDetailImpl userDetail, RolesRepository rolesRepository, EmailVerificationService emailVerificationService, TransactionService transactionService, CategoryRepository categoryRepository, AccountService accountService, ExpenseService expenseService, GoalsService goalsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userDetail = userDetail;
        this.rolesRepository = rolesRepository;
        this.emailVerificationService = emailVerificationService;
        this.transactionService = transactionService;
        this.categoryRepository = categoryRepository;
        this.accountService = accountService;
        this.expenseService = expenseService;
        this.goalsService = goalsService;
    }

    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) {

        //Enable email verification
        if (registerUserDTO.isENABLED()) {
            if (emailVerificationService.isEmailValid(registerUserDTO.getEmail())) {
                throw new RuntimeException("Invalid email.");
            }
        }

        User user = mapUser(registerUserDTO);

        //First registered user will be Admin
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
        transactionService.saveAllAndFlush(user.getAccounts().get(0).getExpenseTransactionHistory());
        accountService.saveAllAndFlush(user.getAccounts());
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
        user.setCategories(setBasicCategories());
        return user;
    }

    private List<Category> setBasicCategories() {


        return categoryRepository.findAllByisBasic(true);
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
        transactionService.saveAndFlush(transaction);
        user.getTransactions().add(transaction);
        user.getAccounts().add(account);
        userRepository.saveAndFlush(user);
        account.getExpenseTransactionHistory().add(transaction);
        accountService.saveAndFlush(account);
    }

    @Override
    public void addExpense(String email, ExpenseDTO expenseDTO) {
        User user = this.userRepository.getByEmail(email);
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        Account accountToUse = user.getAccounts()
                .stream().filter(acc -> acc.getName().equals(expenseDTO.getAccountToUse())).findFirst().get();

        expense.setAccount(accountToUse);
        expense.setUser(user);
        expense.setCategory(this.categoryRepository.findByCategory(expenseDTO.getCategory()));
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
                //if the expense is one-time-buy, it will appear next to its name
                if (expense.getPeriod().equals("custom")) {
                    expense.setName(String.format("%s /One-Time-Buy/", expense.getName()));
                }
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
        expenseService.saveAndFlush(expense);
        accountService.saveAndFlush(accountToUse);
        user.getExpenses().add(expense);
        userRepository.saveAndFlush(user);
    }


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
            if (goalDTO.getCurrentAmount().equals(goalDTO.getAmountToBeSaved()) || goalDTO.getCurrentAmount().compareTo(goalDTO.getAmountToBeSaved()) > 0) {
                goal.setCompleted(true);
                goal.setCompletedOn(LocalDate.now());
                goal.setCurrentAmount(goalDTO.getAmountToBeSaved());
                BigDecimal newAccAmount = account.getCurrentAmount().subtract(goalDTO.getAmountToBeSaved());
                account.setCurrentAmount(newAccAmount);
            } else {
                BigDecimal newAccAmount = account.getCurrentAmount().subtract(goalDTO.getCurrentAmount());
                account.setCurrentAmount(newAccAmount);
            }
        }
        Transaction transaction = this.transactionService.createGoalTransaction(goal);
        user.getTransactions().add(transaction);
        account.getExpenseTransactionHistory().add(transaction);
        user.getGoals().add(goal);
        transactionService.saveAndFlush(transaction);
        accountService.saveAndFlush(account);
        userRepository.saveAndFlush(user);
        goalsService.saveAndFlush(goal);
    }

    @Override
    public void changeUserPlan(ChangeMembershipDTO changePlanDTO, String email) {
        User user = userRepository.getByEmail(email);
        int newPlanAccountsAllowed = switch (changePlanDTO.getMembership()) {
            case "FREE" -> 1;
            case "GOLD" -> 2;
            case "PREMIUM" -> 10;
            default -> 0;
        };

        if (newPlanAccountsAllowed < user.getAccounts().size()) {
            throw new RuntimeException("Can't downgrade plan, too many accounts!");
        }

        user.setAccountNameAssignedForSubscription(changePlanDTO.getAccountToUse());
        changeUserMembership(user, changePlanDTO.getMembership());

        userRepository.saveAndFlush(user);
    }

    @Override
    public AllUsersInfoDTO getAllUsersInfo() {
        List<User> allUsers = userRepository.findAll();
        List<UserFullDetailsInfoDTO> mappedUsers = Arrays.stream(modelMapper.map(allUsers, UserFullDetailsInfoDTO[].class)).toList();
        return new AllUsersInfoDTO(mappedUsers);
    }

    @Override
    public AllUsersInfoDTO filterAllUsersByEmail(String email) {
        Optional<List<User>> allUsers = userRepository.findAllByEmailContaining(email);
        if (allUsers.isEmpty()) {
            return null;
        }
        List<UserFullDetailsInfoDTO> mappedUsers = Arrays.stream(modelMapper.map(allUsers.get(), UserFullDetailsInfoDTO[].class)).toList();
        return new AllUsersInfoDTO(mappedUsers);
    }

    @Override
    public UserFullDetailsInfoDTO getUserById(String userId) {
        User user = this.userRepository.getReferenceById(UUID.fromString(userId));
        return modelMapper.map(user, UserFullDetailsInfoDTO.class);
    }

    @Override
    public void updateUser(UserChangeInformationDTO userChangeInformationDTO) {
        Optional<User> userOpt = userRepository.findById(UUID.fromString(userChangeInformationDTO.getId()));
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!user.getMembershipType().toString().equals(userChangeInformationDTO.getMembership())) {
                changeUserMembership(user, userChangeInformationDTO.getMembership());
            }
            if (!userChangeInformationDTO.getNewFirstName().trim().equals("")) {
                user.setFirstName(userChangeInformationDTO.getNewFirstName());
            }
            if (!userChangeInformationDTO.getNewLastName().trim().equals("")) {
                user.setLastName(userChangeInformationDTO.getNewLastName());
            }
            if (!userChangeInformationDTO.getNewPhoneNumber().trim().equals("")) {
                user.setPhoneNumber(userChangeInformationDTO.getNewPhoneNumber());
            }
            if (userChangeInformationDTO.isPromoteUser()) {
                UserRoleEntity ADMIN = rolesRepository.getReferenceById(2L);
                UserRoleEntity USER = rolesRepository.getReferenceById(1L);
                user.setRoles(List.of(ADMIN, USER));
            }
            if (userChangeInformationDTO.isDemoteAdmin()) {
                UserRoleEntity USER = rolesRepository.getReferenceById(1L);
                user.setRoles(List.of(USER));
            }
            try {
                userRepository.saveAndFlush(user);

            } catch (UnsupportedOperationException ex) {
                userRepository.saveAndFlush(user);

            }
        } else {
            throw new RuntimeException("User not found!");
        }

    }

    private void changeUserMembership(User user, String membership) {
        switch (membership) {
            case "FREE" -> {
                user.setMembershipType(MembershipType.FREE);
                user.setUserAccountsAllowed(1);
            }
            case "GOLD" -> {
                user.setMembershipType(MembershipType.GOLD);
                user.setUserAccountsAllowed(2);
            }
            case "PREMIUM" -> {
                user.setMembershipType(MembershipType.PREMIUM);
                user.setUserAccountsAllowed(10);
            }
        }
        //Set registeredOn, this will be used for account subscription reference
        user.setRegisteredOnDate(LocalDate.now());
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.getByEmail(email);
    }

    @Override
    public void saveAndFlush(User user) {
        this.userRepository.saveAndFlush(user);
    }

}
