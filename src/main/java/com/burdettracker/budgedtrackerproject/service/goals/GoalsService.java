package com.burdettracker.budgedtrackerproject.service.goals;

import com.burdettracker.budgedtrackerproject.model.dto.goal.EditGoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.completed.AllCompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.AllUncompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;

public interface GoalsService {
    AllUncompletedGoalsInfoDTO getAllUncompletedGoals(String currentUserName);

    void deleteGoal(String goalId);

    void editGoal(EditGoalDTO editGoalDTO, String currentUserName);

    AllCompletedGoalsInfoDTO getAllCompletedGoals(String currentUserName);

    void saveAndFlush(Goal goal);
}
