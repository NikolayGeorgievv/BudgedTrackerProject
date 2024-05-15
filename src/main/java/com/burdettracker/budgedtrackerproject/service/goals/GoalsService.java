package com.burdettracker.budgedtrackerproject.service.goals;

import com.burdettracker.budgedtrackerproject.model.dto.goal.AllGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.GoalDTO;

public interface GoalsService {
    AllGoalsInfoDTO getAllGoals(String currentUserName);

    void deleteGoal(String goalId);
}
