package com.burdettracker.budgedtrackerproject.service.user;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.RegisterUserDTO;
import com.burdettracker.budgedtrackerproject.model.entity.*;
import com.burdettracker.budgedtrackerproject.model.entity.enums.CurrencyType;
import com.burdettracker.budgedtrackerproject.model.entity.enums.UserRole;
import com.burdettracker.budgedtrackerproject.repository.*;
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
    private final GoalsRepository goalsRepository;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, ExpenseRepository expenseRepository, UserDetailImpl userDetail, AccountRepository accountRepository, TransactionRepository transactionRepository, GoalsRepository goalsRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.expenseRepository = expenseRepository;
        this.userDetail = userDetail;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.goalsRepository = goalsRepository;
    }

    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) {

        User user = mapUser(registerUserDTO);

        //First registered user will be Admin
        if (this.userRepository.count() == 0){
            user.setRole(UserRole.ADMIN);
        }else{
            user.setRole(UserRole.USER);
        }

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
    public UserExpensesDetailsDTO getUserByEmail(String email) {
        User userByEmail = userRepository.getByEmail(email);
       return modelMapper.map(userByEmail, UserExpensesDetailsDTO.class);
    }



    private User mapUser(RegisterUserDTO registerUserDTO){

        User user = modelMapper.map(registerUserDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

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


    @Override
    public void addAccount(String email, AccountDTO accountDTO) {
        User user = this.userRepository.getByEmail(email);
        Account account = new Account(
                accountDTO.getName(),
                LocalDate.now(),
                accountDTO.getCurrencyType(),
                accountDTO.getCurrentAmount(),
                new ArrayList<>(),
                user
        );
        user.getAccounts().add(account);
        userRepository.saveAndFlush(user);
        accountRepository.saveAndFlush(account);
    }
    //TODO: MOVE THIS TO EXPENSE SERVICE
    @Override
    public void addExpense(String email, ExpenseDTO expenseDTO) {
        User user = this.userRepository.getByEmail(email);
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        Account accountToUse = user.getAccounts()
                .stream().filter(acc -> acc.getName().equals(expenseDTO.getAccountToUse())).findFirst().get();

        expense.setAccount(accountToUse);
        expense.setUser(user);

        if (expenseDTO.getPeriodDate().equals("")){
            //period = yearly or one-time-buy
            //period date needs to be set
            String[] dateData = expenseDTO.getDateDue().split("-");
            int day = Integer.parseInt(dateData[0]);
            String monthNormalCasing = dateData[1];
            String month = dateData[1].toUpperCase();
            int year = Integer.parseInt(dateData[2]);

            //check if the given date is in the future
            if (LocalDate.of(year, Month.valueOf(month), day).isAfter(LocalDate.now()) || LocalDate.of(year, Month.valueOf(month), day).equals(LocalDate.now())){
                //TODO: CHECK IF THE BILL IS ONE-TIME-BUY
                expense.setDateDue(LocalDate.of(year, Month.valueOf(month.toUpperCase()), day));
                expense.setPeriodDate(String.format("%d-%s-%d", day,monthNormalCasing,year));
            }else {
                throw new RuntimeException("Date should be in the future!");
            }
        } else if (expenseDTO.getDateDue().equals("")) {
            //period = weekly or monthly
            //dateDue needs to be set

            if (expenseDTO.getPeriod().equals("weekly")){

                String periodDateDay = expenseDTO.getPeriodDate();
                int periodDateDayValue = getPeriodDateDayValue(periodDateDay);

                for (int i = 0; i < 7; i++) {
                    LocalDate dateToCheck = LocalDate.now().plusDays(i);

                    DayOfWeek neededDay = DayOfWeek.of(periodDateDayValue);
                    if (dateToCheck.getDayOfWeek().equals(neededDay)){
                        expense.setDateDue(dateToCheck);
                    }
                }

            }else if (expenseDTO.getPeriod().equals("monthly")){
                String periodDateDay = expenseDTO.getPeriodDate();
                // 20th  => yyyy MM 20
                int todayDayOfMonth = LocalDate.now().getDayOfMonth();
                int totalMonthDays = LocalDate.now().lengthOfMonth();
                int periodDateDayValue = 0;
                if (periodDateDay.equals("Last day of month")){
                    periodDateDayValue = totalMonthDays;
                }else {
                    char[] dateDayCharArr = periodDateDay.toCharArray();
                    String result;
                    if (periodDateDay.length() == 3){
                        //0-9
                        result = String.valueOf(dateDayCharArr[0]);
                        periodDateDayValue = Integer.parseInt(result);
                    }else {
                        //10-30
                        result = String.valueOf(dateDayCharArr[0] + dateDayCharArr[1]);
                        periodDateDayValue = Integer.parseInt(result);
                    }
                }
                //Check where DateDue is based on today's date.
                if (todayDayOfMonth <= periodDateDayValue){
                    //month stays the same
                    LocalDate dateToSet = LocalDate.of(
                            LocalDate.now().getYear(),
                            LocalDate.now().getMonth(),
                            periodDateDayValue);
                    expense.setDateDue(dateToSet);
                }else{
                    //month +1
                    //TODO: CHECK IF +1 MONTH WORKS CORRECTLY FOR TURNING YEAR AHEAD
                    LocalDate dateToSet = LocalDate.of(
                            LocalDate.now().getYear(),
                            LocalDate.now().getMonth().plus(1),
                            periodDateDayValue);
                    expense.setDateDue(dateToSet);
                }

            }
        }

        accountToUse.getExpenses().add(expense);
        accountRepository.saveAndFlush(accountToUse);
        user.getExpenses().add(expense);
        userRepository.saveAndFlush(user);
        expenseRepository.saveAndFlush(expense);
    }

    //TODO: TAKE THOSE METHODS IN A UTIL CLASS
    private int getPeriodDateDayValue(String periodDateDay) {
        int result = 0;
        switch (periodDateDay){
            case "Monday": result = 1;break;
            case "Tuesday":  result = 2;break;
            case "Wednesday":  result = 3;break;
            case "Thursday":  result = 4;break;
            case "Friday":  result = 5;break;
            case "Saturday":  result = 6;break;
            case "Sunday":  result = 7;break;
        }
        return result;
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
        if (goal.getCurrentAmount() == null){
            goal.setCurrentAmount(BigDecimal.ZERO);
        }
        user.getGoals().add(goal);
        userRepository.saveAndFlush(user);
        goalsRepository.saveAndFlush(goal);
    }

}
