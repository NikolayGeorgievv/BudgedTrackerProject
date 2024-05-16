package com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted;

import java.util.List;

public class AllUncompletedGoalsInfoDTO {
    private List<GoalDTO> allGoals;

    public AllUncompletedGoalsInfoDTO(List<GoalDTO> allGoals) {
        this.allGoals = allGoals;
    }

    public List<GoalDTO> getAllGoals() {
        return allGoals;
    }

    public void setAllGoals(List<GoalDTO> allGoals) {
        this.allGoals = allGoals;
    }
}
