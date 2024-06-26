package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.EditExpenseInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.model.entity.UserRoleEntity;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.model.entity.enums.UserRoleEnum;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("myEmail@example.com")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ExpensesControllerTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpensesController expensesController;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        User user = createDummyUser();
        Account account = createDummyAccount();
        account = accountRepository.saveAndFlush(account);
        user.getAccounts().add(account);
        userRepository.saveAndFlush(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        accountRepository.deleteAll();
        expenseRepository.deleteAll();
    }

    @Test
    void addExpenseValid() {
        ExpenseDTO expenseDTO = createDummyExpenseDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        expensesController.addExpense(expenseDTO, bindingResult);

        assertEquals(1, expenseRepository.findAll().size());
    }

    @Test
    void addExpenseValidRedirect() {
        ExpenseDTO expenseDTO = createDummyExpenseDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = expensesController.addExpense(expenseDTO, bindingResult);

        assertEquals(1, expenseRepository.findAll().size());
        Assertions.assertEquals("redirect:/allExpensesPage", viewName);
    }

    @Test
    void addExpenseInvalid() {
        ExpenseDTO expenseDTO = createDummyExpenseDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        expensesController.addExpense(expenseDTO, bindingResult);

        assertEquals(0, expenseRepository.findAll().size());

        String viewName = expensesController.addExpense(expenseDTO, bindingResult);
        Assertions.assertEquals("/allExpensesPage", viewName);
    }

    @Test
    void deleteExpense() {
        ExpenseDTO expenseDTO = createDummyExpenseDTO();
        expensesController.addExpense(expenseDTO, bindingResult);
        assertEquals(1, expenseRepository.findAll().size());

        expensesController.deleteExpense("1");
        assertEquals(0, expenseRepository.findAll().size());
    }

    @Test
    void editExpense() {
        Expense expense = createDummyExpense();
        Account account = accountRepository.findFirstById(1L);
        expense.setAccount(account);
        expenseRepository.saveAndFlush(expense);

        EditExpenseInfoDTO editExpenseInfoDTO = new EditExpenseInfoDTO(
                "1",
                "updatedName",
                "MyTestAcc",
                "weekly",
                "",
                BigDecimal.valueOf(200),
                "Sunday"
        );

        expensesController.editExpense(editExpenseInfoDTO);
        Expense updatedExpense = expenseRepository.findById(1L).get();
        assertEquals("updatedName", updatedExpense.getName());
        assertEquals("weekly", updatedExpense.getPeriod());
        assertEquals("Sunday", updatedExpense.getPeriodDate());

        String viewName = expensesController.editExpense(editExpenseInfoDTO);
        Assertions.assertEquals("redirect:/allExpensesPage", viewName);

    }

    @Test
    void sortByCategory() {

        ExpenseDTO expenseDTO = createDummyExpenseDTO();
        expensesController.addExpense(expenseDTO, bindingResult);
        assertEquals(1, expenseRepository.findAll().size());

        String viewName = expensesController.sortByCategory("GENERAL", model);
        Assertions.assertEquals("/categorizedExpenses", viewName);
    }

    private User createDummyUser() {
        UUID uuid = new UUID(5, 10);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRole(UserRoleEnum.ADMIN);
        User user = new User(uuid,
                MembershipType.PREMIUM,
                "firstName",
                "lastName",
                "myEmail@example.com",
                "phoneNumber",
                "test",
                10,
                LocalDate.now(),
                "MyAccount",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                List.of(userRoleEntity));
        rolesRepository.saveAllAndFlush(List.of(userRoleEntity));

        return user;
    }

    private ExpenseDTO createDummyExpenseDTO() {
        return new ExpenseDTO("1", "expenseName", "monthly", "", BigDecimal.valueOf(100), "GENERAL", "MyTestAcc", "21st");
    }

    private Account createDummyAccount() {
        Account account = new Account();
        account.setName("MyTestAcc");
        account.setCreatedOn(LocalDate.now());
        account.setCurrentAmount(BigDecimal.ZERO);

        return account;
    }

    private Expense createDummyExpense() {
        Expense expense = new Expense();
        expense.setName("expenseName");
        expense.setAssigned(BigDecimal.valueOf(100));
        expense.setPeriod("monthly");
        expense.setPeriodDate("21st");
        return expense;
    }
}