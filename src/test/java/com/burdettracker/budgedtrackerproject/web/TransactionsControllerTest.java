package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.expense.ExpenseDTO;
import com.burdettracker.budgedtrackerproject.model.dto.transaction.AccountTransactionsDTO;
import com.burdettracker.budgedtrackerproject.model.dto.user.UserExpensesDetailsDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Transaction;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import com.burdettracker.budgedtrackerproject.repository.TransactionRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.category.CategoryService;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.goals.GoalsService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;

import static com.burdettracker.budgedtrackerproject.utils.TestUtils.createDummyAccount;
import static com.burdettracker.budgedtrackerproject.utils.TestUtils.createDummyUser;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.data.repository.util.ClassUtils.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("myEmail@example.com")
class TransactionsControllerTest {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionsController transactionsController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    void getAccountTransactions() throws Exception {
        User user = createDummyUser(rolesRepository);
        userRepository.saveAndFlush(user);

        Transaction transaction = new Transaction();
        transaction.setPaidTo("Test");
        transaction.setAmount(BigDecimal.valueOf(100));
        transactionRepository.saveAndFlush(transaction);

        Account account = createDummyAccount();
        account.setExpenseTransactionHistory(List.of(transaction));
        accountRepository.saveAndFlush(account);

        mockMvc.perform(get("/accounts/" + account.getId() + "/transactions"))
                .andExpect(status().isOk())
                .andExpect(view().name("singleAccountTransactionHistory"))
                .andExpect(model().attributeExists("accountTransactionsDTO", "currentAccountDTO"));

    }


    @Test
    void getAllTransactions() throws Exception {
        User user = createDummyUser(rolesRepository);
        userRepository.saveAndFlush(user);


        mockMvc.perform(get("/getAllTransactions"))
                .andExpect(status().isOk())
                .andExpect(view().name("/allTransactionsPage"));
    }
}