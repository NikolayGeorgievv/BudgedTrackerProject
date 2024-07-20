package com.burdettracker.budgedtrackerproject.service.goals;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.completed.AllCompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.completed.CompletedGoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.AllUncompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;
import com.burdettracker.budgedtrackerproject.repository.GoalsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GoalsServiceImplTest {


    @Mock
    private GoalsRepository goalsRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GoalsServiceImpl goalsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteGoalRemovesGoal() {
        String goalId = "1";

        goalsService.deleteGoal(goalId);

        verify(goalsRepository).deleteById(Long.parseLong(goalId));
    }

    @Test
    void getAllCompletedGoalsReturnsCorrectData() {

        String currentUserName = "test@test.com";
        Goal goal1 = new Goal();
        goal1.setCompleted(true);
        Goal goal2 = new Goal();
        goal2.setCompleted(true);
        List<Goal> goals = Arrays.asList(goal1, goal2);
        when(goalsRepository.getAllByUser_Email(currentUserName)).thenReturn(goals);
        CompletedGoalDTO completedGoalDTO1 = new CompletedGoalDTO();
        completedGoalDTO1.setCompleted(true);
        CompletedGoalDTO completedGoalDTO2 = new CompletedGoalDTO();
        completedGoalDTO2.setCompleted(true);
        when(modelMapper.map(any(), any())).thenReturn(new CompletedGoalDTO[]{completedGoalDTO1, completedGoalDTO2});

        AllCompletedGoalsInfoDTO result = goalsService.getAllCompletedGoals(currentUserName);

        assertEquals(2, result.getCompletedGoals().size());
        verify(goalsRepository).getAllByUser_Email(currentUserName);

    }

    @Test
    void saveAndFlushSavesGoal() {
        Goal goal = new Goal();

        goalsService.saveAndFlush(goal);

        verify(goalsRepository).saveAndFlush(goal);
    }

    @Test
    void getAllUncompletedGoalsReturnsCorrectData() {

        String currentUserName = "test@test.com";
        Goal goal1 = new Goal();
        goal1.setCompleted(false);
        Goal goal2 = new Goal();
        goal2.setCompleted(false);
        List<Goal> goals = Arrays.asList(goal1, goal2);

        when(goalsRepository.getAllByUser_Email(currentUserName)).thenReturn(goals);

        GoalDTO uncompletedGoalDTO1 = new GoalDTO();
        uncompletedGoalDTO1.setCompleted(false);
        GoalDTO uncompletedGoalDTO2 = new GoalDTO();
        uncompletedGoalDTO1.setCompleted(false);
        when(modelMapper.map(any(), any())).thenReturn(new GoalDTO[]{uncompletedGoalDTO1, uncompletedGoalDTO2});

        AllUncompletedGoalsInfoDTO result = goalsService.getAllUncompletedGoals(currentUserName);

        assertEquals(2, result.getAllGoals().size());
        verify(goalsRepository).getAllByUser_Email(currentUserName);
    }

}