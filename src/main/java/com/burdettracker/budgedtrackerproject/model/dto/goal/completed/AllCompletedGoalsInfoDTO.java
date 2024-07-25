package com.burdettracker.budgedtrackerproject.model.dto.goal.completed;

import java.util.List;

public class AllCompletedGoalsInfoDTO {

    private List<CompletedGoalDTO> completedGoals;

    public AllCompletedGoalsInfoDTO() {
    }

    public AllCompletedGoalsInfoDTO(List<CompletedGoalDTO> completedGoals) {
        this.completedGoals = completedGoals;
    }

    public List<CompletedGoalDTO> getCompletedGoals() {
        return completedGoals;
    }

    public void setCompletedGoals(List<CompletedGoalDTO> completedGoals) {
        this.completedGoals = completedGoals;
    }
}
