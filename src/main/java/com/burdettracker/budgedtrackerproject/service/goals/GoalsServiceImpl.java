package com.burdettracker.budgedtrackerproject.service.goals;

import com.burdettracker.budgedtrackerproject.model.dto.goal.completed.AllCompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.completed.CompletedGoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.AllUncompletedGoalsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.EditGoalDTO;
import com.burdettracker.budgedtrackerproject.model.dto.goal.uncompleted.GoalDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.GoalsRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.service.account.AccountService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
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
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final UserRepository userRepository;

    public GoalsServiceImpl(GoalsRepository goalsRepository, ModelMapper modelMapper, AccountService accountService, TransactionService transactionService, UserRepository userRepository) {
        this.goalsRepository = goalsRepository;
        this.modelMapper = modelMapper;
        this.accountService = accountService;
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

//        Goal goalToEdit = this.goalsRepository.getReferenceById(Long.valueOf(editGoalDTO.getId()));
        Goal goalToEdit = this.goalsRepository.findById(Long.valueOf(editGoalDTO.getId())).get();
        if (!goalToEdit.getName().equals(newGoalName) && !newGoalName.trim().equals("")) {
            goalToEdit.setName(newGoalName);
        }
        if (isNewPrimary) {

            Account newAcc = this.accountService.getByName(newAccountToUse);
            goalToEdit.setAccount(newAcc);
            goalToEdit.setAccountToUse(newAccountToUse);
        }
        if (!newDescription.trim().equals("")) {
            goalToEdit.setDescription(newDescription);
        }

        if (addedAmount != null) {
            User user = this.userRepository.getByEmail(currentUserName);
            //Updating the account's amount.(Negative results are allowed)
            Account acc = this.accountService.getByName(newAccountToUse);


            BigDecimal amountDifference = goalToEdit.getAmountToBeSaved().subtract(goalToEdit.getCurrentAmount()).subtract(editGoalDTO.getAddedAmount());
            BigDecimal abs = BigDecimal.ZERO;
            BigDecimal transactionAmount = addedAmount;
            if (amountDifference.compareTo(BigDecimal.ZERO) < 0) {
                abs = amountDifference.abs();
                transactionAmount = addedAmount.subtract(amountDifference.abs());
            }


            BigDecimal newAccAmount = acc.getCurrentAmount().subtract(addedAmount).add(abs);
            acc.setCurrentAmount(newAccAmount);
            this.accountService.saveAndFlush(acc);
            this.transactionService.fundGoalTransaction(goalToEdit, transactionAmount, user);

            goalToEdit.setCurrentAmount(goalToEdit.getCurrentAmount().add(addedAmount));
            if (goalToEdit.getCurrentAmount().compareTo(goalToEdit.getAmountToBeSaved()) >= 0) {
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
    public void saveAndFlush(Goal goal) {
        goalsRepository.saveAndFlush(goal);
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
