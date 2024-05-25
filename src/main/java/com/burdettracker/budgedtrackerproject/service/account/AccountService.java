package com.burdettracker.budgedtrackerproject.service.account;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;

public interface AccountService {
    AllAccountsInfoDTO getAllAccounts(String email);

    void updateAccountById(EditAccountInfoDTO editAccountInfoDTO, String currentUserName);

    void deleteAccountById(String accountId);

    Account getByName(String name);

    AccountDTO getAccountDTOById(String accountId);
}
