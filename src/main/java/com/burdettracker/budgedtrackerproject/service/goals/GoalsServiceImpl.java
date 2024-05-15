package com.burdettracker.budgedtrackerproject.service.goals;

import com.burdettracker.budgedtrackerproject.model.dto.goal.AllGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.GoalDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;
import com.burdettracker.budgedtrackerproject.repository.GoalsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GoalsServiceImpl implements GoalsService {

    private final GoalsRepository goalsRepository;
    private final ModelMapper modelMapper;

    public GoalsServiceImpl(GoalsRepository goalsRepository, ModelMapper modelMapper) {
        this.goalsRepository = goalsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AllGoalsInfoDTO getAllGoals(String currentUserName) {
        List<Goal> allByUserEmail = this.goalsRepository.getAllByUser_Email(currentUserName);
        List<GoalDTO> goalsList = Arrays.stream(modelMapper.map(allByUserEmail, GoalDTO[].class)).toList();
        AllGoalsInfoDTO allGoalsInfoDTO = new AllGoalsInfoDTO(goalsList);
        return allGoalsInfoDTO;
    }

    @Override
    public void deleteGoal(String goalId) {
        this.goalsRepository.deleteById(Long.valueOf(goalId));
    }
}
