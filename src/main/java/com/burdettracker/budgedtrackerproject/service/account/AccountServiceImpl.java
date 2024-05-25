package com.burdettracker.budgedtrackerproject.service.account;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.ExpenseRepository;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final ExpenseRepository expenseRepository;
    private final TransactionService transactionService;
    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper, ExpenseRepository expenseRepository, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.expenseRepository = expenseRepository;
        this.transactionService = transactionService;
    }

    @Override
    public AllAccountsInfoDTO getAllAccounts(String email) {
        List<Account> allAccountsByUserEmail = accountRepository.getAllByUser_Email(email);
        List<AccountDTO> accList = Arrays.stream(modelMapper.map(allAccountsByUserEmail, AccountDTO[].class)).toList();
        AllAccountsInfoDTO allAccountsInfoDTO = new AllAccountsInfoDTO(accList,BigDecimal.valueOf(getTotalBalance(accList)));
        return allAccountsInfoDTO;
    }

    @Override
    public void updateAccountById(EditAccountInfoDTO editAccountInfoDTO) {
        Long accountId = Long.parseLong(editAccountInfoDTO.getId());
        String newName = editAccountInfoDTO.getNewAccountName();;
        BigDecimal amountToAdd = editAccountInfoDTO.getAddedAmount();

        Account account = accountRepository.findFirstById(accountId);
        if (!newName.equals(account.getName()) && !newName.trim().equals("")){
            account.setName(newName);
        }
        if (amountToAdd != null){
            this.transactionService.AddFundsTransaction(editAccountInfoDTO, account);
            account.setCurrentAmount(account.getCurrentAmount().add(amountToAdd));
        }

        accountRepository.saveAndFlush(account);
    }

    @Override
    @Transactional
    public void deleteAccountById(String accountId) {
        this.expenseRepository.deleteByAccountId(Long.parseLong(accountId));
        this.accountRepository.deleteById(Long.parseLong(accountId));
    }

    @Override
    public Account getByName(String name) {
      return this.accountRepository.getByName(name);
    }

    @Override
    public AccountDTO getAccountDTOById(String accountId) {
        Account account = this.accountRepository.getReferenceById(Long.valueOf(accountId));
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        return accountDTO;
    }


    public double getTotalBalance(List<AccountDTO> accList){
        double totalBalance = accList.stream().mapToDouble(accountDTO -> Double.parseDouble(String.valueOf(accountDTO.getCurrentAmount()))).sum();

        return totalBalance;
    }
}
