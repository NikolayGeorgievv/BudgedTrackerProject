package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.goal.EditGoalDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.GoalsRepository;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.List;

import static com.burdettracker.budgedtrackerproject.utils.TestUtils.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("myEmail@example.com")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GoalsControllerTest {

    @Autowired
    private GoalsRepository goalsRepository;

    @Autowired
    private GoalsController goalsController;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void allGoalsPage() {
        String viewName = goalsController.allGoalsPage();
        Assertions.assertEquals("/allGoalsPage", viewName);
    }

    @Test
    void editGoalValid() {
        Account account = createDummyAccount();
        Account account1 = createDummyAccount();
        account1.setName("myNewAccount");
        User user = createDummyUser(rolesRepository);
        user = userRepository.saveAndFlush(user);
        account = accountRepository.saveAndFlush(account);
        account1 = accountRepository.saveAndFlush(account1);

        Goal goal = createDummyGoal();
        goal.setUser(user);
        goal.setAccount(account);
        goalsRepository.saveAndFlush(goal);


        EditGoalDTO editGoalDTO = createDummyEditGoalDTO();
        goalsController.editGoal(editGoalDTO);
        Goal updatedGoal = goalsRepository.findById(1L).get();


        Assertions.assertEquals(updatedGoal.getName(), editGoalDTO.getNewGoalName());
        Assertions.assertEquals(updatedGoal.getDescription(), editGoalDTO.getDescription());
        Assertions.assertEquals(updatedGoal.getAccountToUse(), account1.getName());
        Assertions.assertEquals(updatedGoal.getCurrentAmount().setScale(1), BigDecimal.valueOf(150.0));
    }

    @Test
    void addGoal() {


    }

    @Test
    void deleteGoal() {
    }


}