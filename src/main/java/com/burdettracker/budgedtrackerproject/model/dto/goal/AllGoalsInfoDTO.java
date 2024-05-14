package com.burdettracker.budgedtrackerproject.model.dto.goal;

import java.util.List;

public class AllGoalsInfoDTO {
    private List<GoalDTO> allGoals;

    public AllGoalsInfoDTO(List<GoalDTO> allGoals) {
        this.allGoals = allGoals;
    }

    public List<GoalDTO> getAllGoals() {
        return allGoals;
    }

    public void setAllGoals(List<GoalDTO> allGoals) {
        this.allGoals = allGoals;
    }
}
