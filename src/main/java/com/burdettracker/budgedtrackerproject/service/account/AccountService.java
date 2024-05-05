package com.burdettracker.budgedtrackerproject.service.account;

import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Expense;

public interface AccountService {
    AllAccountsInfoDTO getAllAccounts(String email);

    void updateAccountById(EditAccountInfoDTO editAccountInfoDTO);

    void deleteAccountById(String accountId);

    Account getByName(String name);

    void updateAccountExpenses(Account account);
}
