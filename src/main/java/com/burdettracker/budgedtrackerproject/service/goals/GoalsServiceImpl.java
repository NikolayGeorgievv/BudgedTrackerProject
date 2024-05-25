package com.burdettracker.budgedtrackerproject.service.goals;

import com.burdettracker.budgedtrackerproject.model.dto.goal.completed.AllCompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.completed.CompletedGoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.AllUncompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.EditGoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.GoalsRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class GoalsServiceImpl implements GoalsService {

    private final GoalsRepository goalsRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final UserRepository userRepository;

    public GoalsServiceImpl(GoalsRepository goalsRepository, ModelMapper modelMapper, AccountRepository accountRepository, TransactionService transactionService, UserRepository userRepository) {
        this.goalsRepository = goalsRepository;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.userRepository = userRepository;
    }

    @Override
    public void deleteGoal(String goalId) {
        this.goalsRepository.deleteById(Long.valueOf(goalId));
    }

    @Override
    public void editGoal(EditGoalDTO editGoalDTO, String currentUserName) {
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
            User user = this.userRepository.getByEmail(currentUserName);
            //Updating the account's amount.(Negative results are allowed)
            Account newAcc = this.accountRepository.getByName(newAccountToUse);
            BigDecimal newAccAmount = newAcc.getCurrentAmount().subtract(addedAmount);
            newAcc.setCurrentAmount(newAccAmount);
            accountRepository.saveAndFlush(newAcc);
            transactionService.fundGoalTransaction(goalToEdit,addedAmount, user);

            goalToEdit.setCurrentAmount(goalToEdit.getCurrentAmount().add(addedAmount));
            if (goalToEdit.getCurrentAmount().compareTo(goalToEdit.getAmountToBeSaved()) >= 0){
                goalToEdit.setCompletedOn(LocalDate.now());
                goalToEdit.setCompleted(true);
            }
        }
        goalsRepository.saveAndFlush(goalToEdit);

    }

    @Override
    public AllCompletedGoalsInfoDTO getAllCompletedGoals(String currentUserName) {
        List<Goal> allByUserEmail = this.goalsRepository.getAllByUser_Email(currentUserName);
        //All goals
        List<CompletedGoalDTO> goalsList = Arrays.stream(modelMapper.map(allByUserEmail, CompletedGoalDTO[].class)).toList();
        //Completed goals
        List<CompletedGoalDTO> completedGoals = goalsList.stream().filter(CompletedGoalDTO::isCompleted).toList();

        AllCompletedGoalsInfoDTO allCompletedGoalsInfoDTO = new AllCompletedGoalsInfoDTO(completedGoals);
        return allCompletedGoalsInfoDTO;
    }
    @Override
    public AllUncompletedGoalsInfoDTO getAllUncompletedGoals(String currentUserName) {
        List<Goal> allByUserEmail = this.goalsRepository.getAllByUser_Email(currentUserName);
        //All goals
        List<GoalDTO> goalsList = Arrays.stream(modelMapper.map(allByUserEmail, GoalDTO[].class)).toList();
        //Uncompleted goals
        List<GoalDTO> uncompletedGoals = goalsList.stream().filter(goal -> !goal.isCompleted()).toList();

        AllUncompletedGoalsInfoDTO allUncompletedGoalsInfoDTO = new AllUncompletedGoalsInfoDTO(uncompletedGoals);
        return allUncompletedGoalsInfoDTO;
    }
}
