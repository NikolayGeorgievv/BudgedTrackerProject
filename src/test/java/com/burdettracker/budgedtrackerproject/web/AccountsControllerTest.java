package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Transaction;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.model.entity.UserRoleEntity;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.model.entity.enums.UserRoleEnum;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import com.burdettracker.budgedtrackerproject.repository.TransactionRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("myEmail@example.com")
class AccountsControllerTest {

    @Autowired
    private AccountsController accountsController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    BindingResult bindingResult;

    User user;

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void editAccount() {


    }

    @Test
    void deleteAccount() {
        Account account = createDummyAccount();
        Account account1 = createDummyAccount();
        accountRepository.saveAllAndFlush(List.of(account, account1));
        User user = createDummyUser();
        user.getAccounts().add(account);
        user.getAccounts().add(account1);
        userRepository.saveAndFlush(user);

        accountsController.deleteAccount("1");

        User updatedUser = userRepository.getByEmail("myEmail@example.com");
        Assertions.assertEquals(1, updatedUser.getAccounts().size());

        String viewName = accountsController.deleteAccount("1");
        Assertions.assertEquals("redirect:/allAccountsPage", viewName);
    }

    @Test
    void addAccount() {
        String email = "myEmail@example.com";
        AccountDTO accountDTO = new AccountDTO("myDTOAccount", BigDecimal.valueOf(200));
        User user = createDummyUser();
        userRepository.saveAndFlush(user);
        accountsController.addAccount(accountDTO, bindingResult);
        User updatedUser = userRepository.getByEmail(email);
        Assertions.assertEquals(1, updatedUser.getAccounts().size());

        String viewName = accountsController.addAccount(accountDTO, bindingResult);
        Assertions.assertEquals("redirect:/allAccountsPage", viewName);
    }

    @Test
    void allAccountPage() {
        String viewName = accountsController.allAccountPage();
        Assertions.assertEquals("/allAccountsPage", viewName);
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

    private Account createDummyAccount(){
        Account account = new Account();
        account.setName("MyTestAcc");
        account.setCreatedOn(LocalDate.now());
        account.setCurrentAmount(BigDecimal.ZERO);

        return account;
    }
}