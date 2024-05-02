package com.burdettracker.budgedtrackerproject.service.account;

import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;

public interface AccountService {
    AllAccountsInfoDTO getAllAccounts(String email);

    void updateAccountById(EditAccountInfoDTO editAccountInfoDTO);
}
