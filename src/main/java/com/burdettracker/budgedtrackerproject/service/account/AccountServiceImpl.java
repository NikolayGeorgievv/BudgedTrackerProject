package com.burdettracker.budgedtrackerproject.service.account;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AllAccountsInfoDTO getAllAccounts(String email) {
        List<Account> allAccountsByUserEmail = accountRepository.getAllByUser_Email(email);
        List<AccountDTO> accList = Arrays.stream(modelMapper.map(allAccountsByUserEmail, AccountDTO[].class)).toList();
        AllAccountsInfoDTO allAccountsInfoDTO = new AllAccountsInfoDTO(accList,BigDecimal.valueOf(getTotalBalance(accList)));
        return allAccountsInfoDTO;
    }

    public double getTotalBalance(List<AccountDTO> accList){
        double totalBalance = accList.stream().mapToDouble(accountDTO -> Double.parseDouble(String.valueOf(accountDTO.getCurrentAmount()))).sum();

        return totalBalance;
    }
}
