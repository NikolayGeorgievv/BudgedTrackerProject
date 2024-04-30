package com.burdettracker.budgedtrackerproject.service.account;

import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;

public interface AccountService {
    AllAccountsInfoDTO getAllAccounts(String email);
}
