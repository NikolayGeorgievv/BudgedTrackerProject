package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.goal.EditGoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

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
        assertEquals("/allGoalsPage", viewName);
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


        assertEquals(updatedGoal.getName(), editGoalDTO.getNewGoalName());
        assertEquals(updatedGoal.getDescription(), editGoalDTO.getDescription());
        assertEquals(updatedGoal.getAccountToUse(), account1.getName());
        assertEquals(updatedGoal.getCurrentAmount().setScale(1), BigDecimal.valueOf(150.0));
    }

    @Test
    void addGoal() {
        Account account = createDummyAccount();
        User user = createDummyUser(rolesRepository);
        user.getAccounts().add(account);
        accountRepository.saveAndFlush(account);
        userRepository.saveAndFlush(user);

        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setName("testGoal");
        goalDTO.setDescription("testDescription");
        goalDTO.setAccountToUse("MyTestAcc");
        goalDTO.setCurrentAmount(BigDecimal.valueOf(50.0));
        goalDTO.setAmountToBeSaved(BigDecimal.valueOf(150.0));
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(false);

        goalsController.addGoal(goalDTO, bindingResult);

        List<Goal> goals = goalsRepository.findAll();
        assertEquals(1, goals.size());
        Goal addedGoal = goals.get(0);
        assertEquals(goalDTO.getName(), addedGoal.getName());
        assertEquals(goalDTO.getDescription(), addedGoal.getDescription());
        assertEquals(goalDTO.getAccountToUse(), addedGoal.getAccountToUse());
        assertEquals(goalDTO.getCurrentAmount().setScale(1), addedGoal.getCurrentAmount().setScale(1));
    }

    @Test
    void deleteGoal() {
        Account account = createDummyAccount();
        User user = createDummyUser(rolesRepository);
        user = userRepository.saveAndFlush(user);
        account = accountRepository.saveAndFlush(account);

        Goal goal = createDummyGoal();
        goal.setUser(user);
        goal.setAccount(account);
        goal = goalsRepository.saveAndFlush(goal);

        goalsController.deleteGoal(String.valueOf(goal.getId()));

        assertFalse(goalsRepository.existsById(goal.getId()));
    }


}