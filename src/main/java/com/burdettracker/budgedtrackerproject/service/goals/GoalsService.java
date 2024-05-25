package com.burdettracker.budgedtrackerproject.service.goals;

import com.burdettracker.budgedtrackerproject.model.dto.goal.completed.AllCompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.AllUncompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.EditGoalDTO;

public interface GoalsService {
    AllUncompletedGoalsInfoDTO getAllUncompletedGoals(String currentUserName);

    void deleteGoal(String goalId);

    void editGoal(EditGoalDTO editGoalDTO, String currentUserName);

    AllCompletedGoalsInfoDTO getAllCompletedGoals(String currentUserName);
}
