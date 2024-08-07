package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.web.advisedControllers.AccountsController;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.List;

import static com.burdettracker.budgedtrackerproject.utils.TestUtils.createDummyAccount;
import static com.burdettracker.budgedtrackerproject.utils.TestUtils.createDummyUser;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("myEmail@example.com")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountsControllerTest {

    @Autowired
    private AccountsController accountsController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @Order(1)
    void editAccount() {
        EditAccountInfoDTO editAccountInfoDTO = new EditAccountInfoDTO();
        editAccountInfoDTO.setId(String.valueOf(1));
        editAccountInfoDTO.setNewAccountName("myAccountNewName");
        editAccountInfoDTO.setAddedAmount(BigDecimal.valueOf(100));

        User user = createDummyUser(rolesRepository);

        Account account = createDummyAccount();
        userRepository.saveAndFlush(user);

        accountRepository.saveAndFlush(account);
        accountsController.editAccount(editAccountInfoDTO);

        Account updatedAccount = accountRepository.getReferenceById(1L);

        Assertions.assertEquals("myAccountNewName", updatedAccount.getName());
        Assertions.assertEquals(BigDecimal.valueOf(100.0), updatedAccount.getCurrentAmount().setScale(1));

        String viewName = accountsController.editAccount(editAccountInfoDTO);
        Assertions.assertEquals("redirect:/allAccountsPage", viewName);
    }

    @Test
    @Order(2)
    void deleteAccount() {
        Account account = createDummyAccount();
        Account account1 = createDummyAccount();
        accountRepository.saveAllAndFlush(List.of(account, account1));
        User user = createDummyUser(rolesRepository);
        user.getAccounts().add(account);
        user.getAccounts().add(account1);
        userRepository.saveAndFlush(user);

        accountsController.deleteAccount("1");

        User updatedUser = userRepository.getByEmail("myEmail@example.com");
        Assertions.assertEquals(2, updatedUser.getAccounts().size());

        String viewName = accountsController.deleteAccount("1");
        Assertions.assertEquals("redirect:/allAccountsPage", viewName);
    }

    @Test
    void addAccount() {
        String email = "myEmail@example.com";
        AccountDTO accountDTO = new AccountDTO("myDTOAccount", BigDecimal.valueOf(200));
        User user = createDummyUser(rolesRepository);
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

}