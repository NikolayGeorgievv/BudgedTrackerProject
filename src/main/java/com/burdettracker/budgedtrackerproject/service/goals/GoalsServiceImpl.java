package com.burdettracker.budgedtrackerproject.service.goals;

import com.burdettracker.budgedtrackerproject.model.dto.goal.AllGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.EditGoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.GoalDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.GoalsRepository;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class GoalsServiceImpl implements GoalsService {

    private final GoalsRepository goalsRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    public GoalsServiceImpl(GoalsRepository goalsRepository, ModelMapper modelMapper, AccountRepository accountRepository) {
        this.goalsRepository = goalsRepository;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
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

    @Override
    public void editGoal(EditGoalDTO editGoalDTO) {
        String newGoalName = editGoalDTO.getNewGoalName();
        String newAccountToUse = editGoalDTO.getAccountToUse();
        BigDecimal addedAmount = editGoalDTO.getAddedAmount();
        String newDescription = editGoalDTO.getDescription();
        boolean isNewPrimary = editGoalDTO.getIsNewPrimary() != null;

        Goal goalToEdit = this.goalsRepository.getReferenceById(Long.valueOf(editGoalDTO.getId()));
        if (!goalToEdit.getName().equals(newGoalName) && !newGoalName.trim().equals("")){
            goalToEdit.setName(newGoalName);
        }
        if (isNewPrimary){
            Account newAcc = this.accountRepository.getByName(newAccountToUse);
            goalToEdit.setAccount(newAcc);
            goalToEdit.setAccountToUse(newAccountToUse);
        }
        if (!newDescription.trim().equals("")){
            goalToEdit.setDescription(newDescription);
        }

        if (addedAmount != null){
            //Updating the account's amount.(Negative results are allowed)
            Account newAcc = this.accountRepository.getByName(newAccountToUse);
            BigDecimal newAccAmount = newAcc.getCurrentAmount().subtract(addedAmount);
            newAcc.setCurrentAmount(newAccAmount);
            accountRepository.saveAndFlush(newAcc);


            goalToEdit.setCurrentAmount(goalToEdit.getCurrentAmount().add(addedAmount));
        }
        goalsRepository.saveAndFlush(goalToEdit);

    }
}
